/** Copyright 2014 Robin Stumm (serverkorken@gmail.com, http://dermetfan.net)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

package net.dermetfan.gdx.graphics.g2d;

import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pools;
import net.dermetfan.gdx.math.GeometryUtils;
import net.dermetfan.utils.Function;

import static net.dermetfan.gdx.physics.box2d.Box2DUtils.height;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.minX;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.minY;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.position;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.width;

/** A {@link Box2DPolygonSprite} is a {@link PolygonSprite} with additional drawing information and the ability to draw itself on a given {@link Body} or {@link Fixture}.
 *  It is supposed to be put in the user data of {@link Fixture Fixtures} or {@link Body Bodies}. Because geometrical information about bodies cannot be cached, it is faster to put Box2DPolygonSprites in the user data of Fixtures.
 *  @author dermetfan
 *  @since 0.5.0 */
public class Box2DPolygonSprite extends PolygonSprite {

	/** for internal, temporary usage */
	private static final Vector2 vec2 = new Vector2();

	/** the {@link #userDataAccessor} used by default */
	public static final Function<Box2DPolygonSprite, Object> defaultUserDataAccessor = new Function<Box2DPolygonSprite, Object>() {
		@Override
		public Box2DPolygonSprite apply(Object userData) {
			return userData instanceof Box2DPolygonSprite ? (Box2DPolygonSprite) userData : null;
		}
	};

	/** the {@link Function} used to get a {@link Box2DPolygonSprite} from the user data of a body or fixture */
	private static Function<Box2DPolygonSprite, Object> userDataAccessor = defaultUserDataAccessor;

	/** @see #draw(Batch, World, boolean) */
	public static void draw(Batch batch, World world) {
		draw(batch, world, false);
	}

	/** draws all the {@link Box2DPolygonSprite Box2DPolygonSprites} on the {@link Body} or {@link Fixture} that hold them in their user data in the given {@link World} */
	public static void draw(Batch batch, World world, boolean sortByZ) {
		@SuppressWarnings("unchecked")
		Array<Body> tmpBodies = Pools.obtain(Array.class);
		world.getBodies(tmpBodies);

		if(sortByZ) {
			@SuppressWarnings("unchecked")
			ObjectMap<Box2DPolygonSprite, Object> tmpZMap = Pools.obtain(ObjectMap.class);
			tmpZMap.clear();
			for(Body body : tmpBodies) {
				Box2DPolygonSprite tmpBox2DPolygonSprite;
				if((tmpBox2DPolygonSprite = userDataAccessor.apply(body.getUserData())) != null)
					tmpZMap.put(tmpBox2DPolygonSprite, body);
				for(Fixture fixture : body.getFixtureList())
					if((tmpBox2DPolygonSprite = userDataAccessor.apply(fixture.getUserData())) != null)
						tmpZMap.put(tmpBox2DPolygonSprite, fixture);
			}

			@SuppressWarnings("unchecked")
			Array<Box2DPolygonSprite> tmpKeys = Pools.obtain(Array.class);
			Iterator<Box2DPolygonSprite> keys = tmpZMap.keys();
			while(keys.hasNext())
				tmpKeys.add(keys.next());
			tmpKeys.sort(zComparator);
			for(Box2DPolygonSprite key : tmpKeys) {
				Object value = tmpZMap.get(key);
				if(value instanceof Body)
					key.draw(batch, (Body) value);
				else
					key.draw(batch, (Fixture) value);
			}

			tmpKeys.clear();
			tmpZMap.clear();
			Pools.free(tmpKeys);
			Pools.free(tmpZMap);
		} else
			for(Body body : tmpBodies) {
				Box2DPolygonSprite tmpBox2DPolygonSprite;
				if((tmpBox2DPolygonSprite = userDataAccessor.apply(body.getUserData())) != null)
					tmpBox2DPolygonSprite.draw(batch, body);
				for(Fixture fixture : body.getFixtureList())
					if((tmpBox2DPolygonSprite = userDataAccessor.apply(fixture.getUserData())) != null)
						tmpBox2DPolygonSprite.draw(batch, fixture);
			}

		tmpBodies.clear();
		Pools.free(tmpBodies);
	}

	/** @return the {@link #zComparator} */
	public static Comparator<Box2DPolygonSprite> getZComparator() {
		return zComparator;
	}

	/** @param zComparator the {@link #zComparator} to set */
	public static void setZComparator(Comparator<Box2DPolygonSprite> zComparator) {
		if(zComparator == null)
			throw new IllegalArgumentException("zComparator must not be null");
		Box2DPolygonSprite.zComparator = zComparator;
	}

	/** @return the {@link #userDataAccessor} */
	public static Function<Box2DPolygonSprite, ?> getUserDataAccessor() {
		return userDataAccessor;
	}

	/** @param userDataAccessor the {@link #userDataAccessor} to set */
	public static void setUserDataAccessor(Function<Box2DPolygonSprite, Object> userDataAccessor) {
		Box2DPolygonSprite.userDataAccessor = userDataAccessor != null ? userDataAccessor : defaultUserDataAccessor;
	}

	/** the z index for sorted drawing */
	private float zIndex;

	/** a {@link Comparator} used to sort {@link Box2DPolygonSprite Box2DPolygonSprites} by their {@link Box2DPolygonSprite#zIndex z index} in {@link #draw(Batch, World)} */
	private static Comparator<Box2DPolygonSprite> zComparator = new Comparator<Box2DPolygonSprite>() {
		@Override
		public int compare(Box2DPolygonSprite s1, Box2DPolygonSprite s2) {
			return s1.zIndex - s2.zIndex > 0 ? 1 : s1.zIndex - s2.zIndex < 0 ? -1 : 0;
		}
	};

	/** if the width and height should be adjusted to those of the {@link Body} or {@link Fixture} this {@link Box2DPolygonSprite} is attached to (true by default) */
	private boolean adjustWidth = true, adjustHeight = true;

	/** if the size should be adjusted to match the polygon onto the body or fixture (true by default) */
	private boolean adjustToPolygon = true;

	/** if the origin of this {@link Box2DPolygonSprite} should be used when it's drawn (false by default) */
	private boolean useOriginX, useOriginY;

	/** @see PolygonSprite#PolygonSprite(PolygonRegion) */
	public Box2DPolygonSprite(PolygonRegion region) {
		super(region);
	}

	/** @see PolygonSprite#PolygonSprite(PolygonSprite) */
	public Box2DPolygonSprite(PolygonSprite sprite) {
		super(sprite);
	}

	/** draws this {@link Box2DPolygonSprite} on the given {@link Fixture} */
	public void draw(Batch batch, Fixture fixture) {
		vec2.set(position(fixture));
		draw(batch, vec2.x, vec2.y, width(fixture), height(fixture), fixture.getBody().getAngle());
	}

	/** draws this {@link Box2DPolygonSprite} on the given {@link Body} */
	public void draw(Batch batch, Body body) {
		float width = width(body), height = height(body);
		vec2.set(minX(body) + width / 2, minY(body) + height / 2);
		vec2.set(body.getWorldPoint(vec2));
		draw(batch, vec2.x, vec2.y, width, height, body.getAngle());
	}

	/** Used internally. Draws this {@code Box2DPolygonSprite} in classic sprite coordinate system fashion with the given Box2D coordinates (combined with its own position, size and rotation).<br>
	 *  If {@link #useOriginX useOriginX/Y} is enabled, the {@link #originX origin} will be used instead of calculating an appropriate one for the given Box2D coordinates.<br>
	 *  If {@link #adjustWidth adjustWidth/Height} is disabled, the size of the drawing area of the sprite will be {@link #width} * {@link #height} instead of the given size.<br>
	 *  The drawing position of the sprite is always the bottom left of the body or fixture.
	 *  @param batch The Batch to draw on. Redirects to {@link #draw(PolygonSpriteBatch, float, float, float, float, float)} if this is an instance of {@link PolygonSpriteBatch}.
	 *  @param box2dX the x coordinate (center) of the body or fixture
	 *  @param box2dY the y coordinate (center) of the body or fixture
	 *  @param box2dWidth the width of the body or fixture
	 *  @param box2dHeight the height of the body or fixture
	 *  @param box2dRotation the rotation of the body or fixture */
	public void draw(Batch batch, float box2dX, float box2dY, float box2dWidth, float box2dHeight, float box2dRotation) {
		if(batch instanceof PolygonSpriteBatch)
			draw((PolygonSpriteBatch) batch, box2dX, box2dY, box2dWidth, box2dHeight, box2dRotation);
		else {
			batch.setColor(getColor());
			batch.draw(getRegion().getRegion(), box2dX - box2dWidth / 2 + getX(), box2dY - box2dHeight / 2 + getY(), useOriginX ? getOriginX() : box2dWidth / 2, useOriginY ? getOriginY() : box2dHeight / 2, adjustWidth ? box2dWidth : getWidth(), adjustHeight ? box2dHeight : getHeight(), getScaleX(), getScaleY(), box2dRotation * MathUtils.radDeg + getRotation());
		}
	}

	/** @see #draw(Batch, float, float, float, float, float) */
	public void draw(PolygonSpriteBatch batch, float box2dX, float box2dY, float box2dWidth, float box2dHeight, float box2dRotation) {
		batch.setColor(getColor());
		float x, y, originX, originY, width, height;
		if(adjustToPolygon) {
			FloatArray vertices = Pools.obtain(FloatArray.class);
			vertices.clear();
			vertices.addAll(getRegion().getVertices());
			float polygonWidth = GeometryUtils.width(vertices), polygonHeight = GeometryUtils.height(vertices);
			float polygonWidthRatio = getRegion().getRegion().getRegionWidth() / polygonWidth, polygonHeightRatio = getRegion().getRegion().getRegionHeight() / polygonHeight;
			width = box2dWidth * polygonWidthRatio;
			height = box2dHeight * polygonHeightRatio;
			float polygonX = GeometryUtils.minX(vertices), polygonY = GeometryUtils.minY(vertices);
			float polygonXRatio = getRegion().getRegion().getRegionWidth() / polygonX, polygonYRatio = getRegion().getRegion().getRegionHeight() / polygonY;
			float offsetX = width / polygonXRatio, offsetY = height / polygonYRatio;
			x = box2dX - offsetX - width / 2 / polygonWidthRatio;
			y = box2dY - offsetY - height / 2 / polygonHeightRatio;
			originX = offsetX + box2dWidth / 2;
			originY = offsetY + box2dHeight / 2;
		} else {
			x = box2dX - box2dWidth / 2;
			y = box2dY - box2dHeight / 2;
			originX = box2dWidth / 2;
			originY = box2dHeight / 2;
			width = box2dWidth;
			height = box2dHeight;
		}
		batch.draw(getRegion(), x + getX(), y + getY(), useOriginX ? getOriginX() : originX, useOriginY ? getOriginY() : originY, adjustWidth ? width : getWidth(), adjustHeight ? height : getHeight(), getScaleX(), getScaleY(), box2dRotation * MathUtils.radDeg + getRotation());
	}

	// getters and setters

	/** @return the {@link #zIndex} */
	public float getZIndex() {
		return zIndex;
	}

	/** @param zIndex the {@link #zIndex} to set */
	public void setZIndex(float zIndex) {
		this.zIndex = zIndex;
	}

	/** @return the {@link #adjustWidth} */
	public boolean isAdjustWidth() {
		return adjustWidth;
	}

	/** @param adjustWidth the {@link #adjustWidth} to set */
	public void setAdjustWidth(boolean adjustWidth) {
		this.adjustWidth = adjustWidth;
	}

	/** @return the {@link #adjustHeight} */
	public boolean isAdjustHeight() {
		return adjustHeight;
	}

	/** @param adjustHeight the {@link #adjustHeight} to set */
	public void setAdjustHeight(boolean adjustHeight) {
		this.adjustHeight = adjustHeight;
	}

	/** @param adjustSize the {@link #adjustWidth} and {@link #adjustHeight} to set */
	public void setAdjustSize(boolean adjustSize) {
		adjustWidth = adjustHeight = adjustSize;
	}

	/** @return the {@link #adjustToPolygon} */
	public boolean isAdjustToPolygon() {
		return adjustToPolygon;
	}

	/** @param adjustToPolygon the {@link #adjustToPolygon} to set */
	public void setAdjustToPolygon(boolean adjustToPolygon) {
		this.adjustToPolygon = adjustToPolygon;
	}

	/** @return the {@link #useOriginX} */
	public boolean isUseOriginX() {
		return useOriginX;
	}

	/** @param useOriginX the {@link #useOriginX} to set */
	public void setUseOriginX(boolean useOriginX) {
		this.useOriginX = useOriginX;
	}

	/** @return the {@link #useOriginY} */
	public boolean isUseOriginY() {
		return useOriginY;
	}

	/** @param useOriginY the {@link #useOriginY} to set */
	public void setUseOriginY(boolean useOriginY) {
		this.useOriginY = useOriginY;
	}

	/** @param useOrigin the {@link #useOriginX} and {@link #useOriginY} to set */
	public void setUseOrigin(boolean useOrigin) {
		useOriginX = useOriginY = useOrigin;
	}

	/** @see Sprite#setSize(float, float) */
	public void setWidth(float width) {
		setSize(width, getHeight());
	}

	/** @see Sprite#setSize(float, float) */
	public void setHeight(float height) {
		setSize(getWidth(), height);
	}

}
