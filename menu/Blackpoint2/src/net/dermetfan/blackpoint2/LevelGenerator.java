package net.dermetfan.blackpoint2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class LevelGenerator {

	private Body environment;
	private float leftEdge, rightEdge, minGap, maxGap, minWidth, maxWidth, height, angle, y;

	public LevelGenerator(Body environment, float leftEdge, float rightEdge, float minGap, float maxGap, float minWidth, float maxWidth, float height, float angle) {
		this.environment = environment;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
		this.minGap = minGap;
		this.maxGap = maxGap;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.height = height;
		this.angle = angle;
	}
	
	public void generate(float topEdge) {
		if(y + MathUtils.random(minGap, maxGap) > topEdge)
			return;
		
		y = topEdge;
		float width = MathUtils.random(minWidth, maxWidth);
		float x = MathUtils.random(leftEdge, rightEdge - width);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2, new Vector2(x + width / 2, y + height / 2), MathUtils.random(-angle / 2, angle / 2));
		
		environment.createFixture(shape, 0);
		
		shape.dispose();
	}

	/** @return the {@link #environment} */
	public Body getEnvironment() {
		return environment;
	}

	/** @param environment the {@link #environment} to set */
	public void setEnvironment(Body environment) {
		this.environment = environment;
	}

	/** @return the {@link #leftEdge} */
	public float getLeftEdge() {
		return leftEdge;
	}

	/** @param leftEdge the {@link #leftEdge} to set */
	public void setLeftEdge(float leftEdge) {
		this.leftEdge = leftEdge;
	}

	/** @return the {@link #rightEdge} */
	public float getRightEdge() {
		return rightEdge;
	}

	/** @param rightEdge the {@link #rightEdge} to set */
	public void setRightEdge(float rightEdge) {
		this.rightEdge = rightEdge;
	}

	/** @return the {@link #minGap} */
	public float getMinGap() {
		return minGap;
	}

	/** @param minGap the {@link #minGap} to set */
	public void setMinGap(float minGap) {
		this.minGap = minGap;
	}

	/** @return the {@link #maxGap} */
	public float getMaxGap() {
		return maxGap;
	}

	/** @param maxGap the {@link #maxGap} to set */
	public void setMaxGap(float maxGap) {
		this.maxGap = maxGap;
	}

	/** @return the {@link #minWidth} */
	public float getMinWidth() {
		return minWidth;
	}

	/** @param minWidth the {@link #minWidth} to set */
	public void setMinWidth(float minWidth) {
		this.minWidth = minWidth;
	}

	/** @return the {@link #maxWidth} */
	public float getMaxWidth() {
		return maxWidth;
	}

	/** @param maxWidth the {@link #maxWidth} to set */
	public void setMaxWidth(float maxWidth) {
		this.maxWidth = maxWidth;
	}

	/** @return the {@link #height} */
	public float getHeight() {
		return height;
	}

	/** @param height the {@link #height} to set */
	public void setHeight(float height) {
		this.height = height;
	}

}
