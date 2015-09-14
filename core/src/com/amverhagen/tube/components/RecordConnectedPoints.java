package com.amverhagen.tube.components;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class RecordConnectedPoints extends com.artemis.Component {
	public ConnectedPointList points;

	public RecordConnectedPoints(int numberOfPoints) {
		this.points = new ConnectedPointList(numberOfPoints);
	}

	public class ConnectedPointList {
		public ConnectedPoint[] connectedPoints;
		private ConnectedPoint mostRecentlyAddedPoint;
		private int currentPointIndex;

		public ConnectedPointList(int numOfPoints) {
			this.connectedPoints = new ConnectedPoint[numOfPoints];
			this.currentPointIndex = 0;
			this.mostRecentlyAddedPoint = null;
		}

		public void addPoint(float x, float y) {
			if (mostRecentlyAddedPoint == null) {
				connectedPoints[currentPointIndex] = new ConnectedPoint(x, y, new ConnectedPoint(x, y, null));
			} else {
				connectedPoints[currentPointIndex] = new ConnectedPoint(x, y, mostRecentlyAddedPoint);
			}
			mostRecentlyAddedPoint = connectedPoints[currentPointIndex];
			currentPointIndex++;
			if (currentPointIndex >= connectedPoints.length - 1) {
				currentPointIndex = 0;
			}
		}

		public ArrayList<ConnectedPoint> getConnectedPoints() {
			ArrayList<ConnectedPoint> cps = new ArrayList<ConnectedPoint>();
			for (int i = 0; i < this.connectedPoints.length; i++) {
				if (connectedPoints[i] != null) {
					cps.add(connectedPoints[i]);
				}
			}
			return cps;
		}

		public class ConnectedPoint {
			public Vector2 pos;
			public ConnectedPoint pointBehind;

			public ConnectedPoint(float x, float y, ConnectedPoint lastPoint) {
				this.pos = new Vector2(x, y);
				this.pointBehind = lastPoint;
			}
		}
	}
}
