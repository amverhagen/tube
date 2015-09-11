package com.amverhagen.tube.components;

import com.badlogic.gdx.math.Vector2;

public class RecordPreviousPoints extends com.artemis.Component {
	public ConnectedPointList points;

	public RecordPreviousPoints() {
		this.points = new ConnectedPointList();
	}

	public class ConnectedPointList {
		public ConnectedPoint[] connectedPoints;
		private ConnectedPoint mostRecentlyAddedPoint;
		private int currentPointIndex;

		public ConnectedPointList() {
			this.connectedPoints = new ConnectedPoint[100];
			this.currentPointIndex = 0;
			this.mostRecentlyAddedPoint = null;
		}

		public void addPoint(float x, float y) {
			connectedPoints[currentPointIndex] = new ConnectedPoint(x, y, mostRecentlyAddedPoint);
			mostRecentlyAddedPoint = connectedPoints[currentPointIndex];
			currentPointIndex++;
			if (currentPointIndex >= connectedPoints.length - 1) {
				currentPointIndex = 0;
			}
		}

		public class ConnectedPoint {
			Vector2 pos;
			ConnectedPoint pointBehind;

			public ConnectedPoint(float x, float y, ConnectedPoint lastPoint) {
				this.pos = new Vector2(x, y);
				this.pointBehind = lastPoint;
			}
		}
	}
}
