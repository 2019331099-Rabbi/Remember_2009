package com.rabbimidu.remember2009.game;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.rabbimidu.remember2009.game.objects.Platform;
import com.rabbimidu.remember2009.game.objects.Rocket;
import com.rabbimidu.remember2009.game.objects.Star;
import com.rabbimidu.remember2009.game.objects.Time;

public class TiledMapManagerBox2d {

	private WorldGame oWorld;
	private World oWorldBox;
	private float m_units;
	private FixtureDef defaultFixture;

	public TiledMapManagerBox2d(WorldGame oWorld, float unitsPerPixel) {
		this.oWorld = oWorld;
		oWorldBox = oWorld.oWorldBox;
		m_units = unitsPerPixel;

		defaultFixture = new FixtureDef();
		defaultFixture.density = 1.0f;
		defaultFixture.friction = .5f;
		defaultFixture.restitution = 0.0f;
	}

	public void createMap(Map map) {
		createBorder(map, "fisicos");
		createCharacters(map, "interaccion");
	}

	private void createCharacters(Map map, String layerName) {
		MapLayer layer = map.getLayers().get(layerName);

		if (layer == null) {
			return;
		}

		MapObjects objects = layer.getObjects();
		for (MapObject object : objects) {
			if (object instanceof TextureMapObject) {
				continue;
			}

			MapProperties properties = object.getProperties();
			String type = (String) properties.get("type");
			if (type.equals("estrella")) {
				createStar(object);
			} else if (type.equals("gas")) {
				createTimeBar(object);
			}
		}
	}

	private void createBorder(Map map, String layerName) {
		MapLayer layer = map.getLayers().get(layerName);

		if (layer == null) {
			return;
		}

		MapObjects objects = layer.getObjects();

		for (MapObject object : objects) {
			if (object instanceof TextureMapObject) {
				continue;
			}

			MapProperties properties = object.getProperties();
			String type = (String) properties.get("type");
			if (type == null)
				continue;
			else if (type.equals("inicio") || type.equals("fin")) {
				createPlatform(object, type);
			}

			Shape shape;
			if (object instanceof RectangleMapObject) {
				shape = getRectangle((RectangleMapObject) object);
			} else if (object instanceof PolygonMapObject) {
				shape = getPolygon((PolygonMapObject) object);
			} else if (object instanceof PolylineMapObject) {
				shape = getPolyline((PolylineMapObject) object);
			} else if (object instanceof CircleMapObject) {
				shape = getCircle((CircleMapObject) object);
			} else {
				continue;
			}

			defaultFixture.shape = shape;

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;

			Body body = oWorldBox.createBody(bodyDef);
			body.createFixture(defaultFixture);

			body.setUserData(type);

			defaultFixture.shape = null;
			defaultFixture.isSensor = false;
			defaultFixture.filter.groupIndex = 0;
			shape.dispose();

		}

	}

	private void createPlatform(MapObject object, String type) {
		Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * m_units, (rectangle.y + rectangle.height * 0.5f) * m_units);
		polygon.setAsBox(rectangle.getWidth() * 0.5f * m_units, rectangle.height * 0.5f * m_units, size, 0.0f);
		defaultFixture.shape = polygon;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = oWorldBox.createBody(bodyDef);
		body.createFixture(defaultFixture);

		float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
		float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

		float height = (rectangle.height * m_units * 0.5f);
		float width = (rectangle.width * m_units * 0.5f);

		Platform platform = new Platform(x, y, width, height);

		body.setUserData(platform);
		oWorld.platforms.add(platform);

		if (type.equals("inicio"))
			createRocket(platform);
		else if (type.equals("fin"))
			platform.isFinal = true;
	}

	private void createRocket(Platform platform) {
		Rocket rocket = new Rocket(platform.position.x, platform.position.y + platform.size.y / 2);

		BodyDef bd = new BodyDef();
		bd.position.x = rocket.position.x;
		bd.position.y = rocket.position.y;
		bd.type = BodyType.DynamicBody;
		Body oBody = oWorldBox.createBody(bd);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Rocket.WIDTH / 2f, Rocket.HEIGHT / 2f);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = Rocket.DENSIDAD_INICIAL;
		fixture.restitution = 0;
		fixture.friction = .5f;
		oBody.createFixture(fixture);

		oBody.setUserData(rocket);
		oBody.setBullet(true);
		shape.dispose();
		oWorld.rocket = rocket;
	}

	private void createStar(MapObject object) {
		Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
		float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
		float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
		float height = (rectangle.height * m_units * 0.5f);
		float width = (rectangle.width * m_units * 0.5f);

		Star obj = new Star(x, y, width, height);
		BodyDef bd = new BodyDef();
		bd.position.y = obj.position.y;
		bd.position.x = obj.position.x;
		bd.type = BodyType.StaticBody;

		PolygonShape pies = new PolygonShape();
		pies.setAsBox(.1f, .1f);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = pies;
		fixture.density = .5f;
		fixture.restitution = 0f;
		fixture.friction = 0f;
		fixture.isSensor = true;

		Body oBody = oWorldBox.createBody(bd);
		oBody.createFixture(fixture);

		oBody.setUserData(obj);

		oWorld.targets.add(obj);
		pies.dispose();

	}

	private void createTimeBar(MapObject object) {
		Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
		float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
		float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
		float height = (rectangle.height * m_units * 0.5f);
		float width = (rectangle.width * m_units * 0.5f);

		Time obj = new Time(x, y, width, height);
		BodyDef bd = new BodyDef();
		bd.position.y = obj.position.y;
		bd.position.x = obj.position.x;
		bd.type = BodyType.StaticBody;

		PolygonShape pies = new PolygonShape();
		pies.setAsBox(.1f, .1f);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = pies;
		fixture.density = .5f;
		fixture.restitution = 0f;
		fixture.friction = 0f;
		fixture.isSensor = true;

		Body oBody = oWorldBox.createBody(bd);
		oBody.createFixture(fixture);

		oBody.setUserData(obj);

		oWorld.times.add(obj);
		pies.dispose();

	}

	private Shape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * m_units, (rectangle.y + rectangle.height * 0.5f) * m_units);
		polygon.setAsBox(rectangle.getWidth() * 0.5f * m_units, rectangle.height * 0.5f * m_units, size, 0.0f);
		return polygon;
	}

	private Shape getCircle(CircleMapObject circleObject) {
		Circle circle = circleObject.getCircle();
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(circle.radius * m_units);
		circleShape.setPosition(new Vector2(circle.x * m_units, circle.y * m_units));
		return circleShape;

	}

	private Shape getPolygon(PolygonMapObject polygonObject) {
		PolygonShape polygon = new PolygonShape();
		float[] vertices = polygonObject.getPolygon().getVertices();
		float[] worldVertices = new float[vertices.length];
		float yLost = polygonObject.getPolygon().getY() * m_units;
		float xLost = polygonObject.getPolygon().getX() * m_units;

		for (int i = 0; i < vertices.length; ++i) {
			if (i % 2 == 0)
				worldVertices[i] = vertices[i] * m_units + xLost;
			else
				worldVertices[i] = vertices[i] * m_units + yLost;
		}
		polygon.set(worldVertices);

		return polygon;
	}

	private Shape getPolyline(PolylineMapObject polylineObject) {
		float[] vertices = polylineObject.getPolyline().getVertices();

		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		float yLost = polylineObject.getPolyline().getY() * m_units;
		float xLost = polylineObject.getPolyline().getX() * m_units;
		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] * m_units + xLost;
			worldVertices[i].y = vertices[i * 2 + 1] * m_units + yLost;
		}
		ChainShape chain = new ChainShape();
		chain.createChain(worldVertices);
		return chain;
	}
}
