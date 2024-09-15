package check;

public class Checker {
    public boolean hit(int x, float y, int r) {
        x = 300 + x * (200 / r);
        y = 300 - y * ((float) 200 / r);
        return inRect(x, y) || inTriangle(x, y) || inCircle(x, y);
    }

    private boolean inRect(int x, float y) {
        return x >= 300 && x <= 500 && y <= 300 && y >= 200;
    }

    private boolean inTriangle(int x, float y) {
        double x1 = 200, y1 = 300;
        double x2 = 300, y2 = 400;
        double x3 = 300, y3 = 300;

        double area = triangleArea(x1, y1, x2, y2, x3, y3);

        double area1 = triangleArea(x, y, x2, y2, x3, y3);
        double area2 = triangleArea(x1, y1, x, y, x3, y3);
        double area3 = triangleArea(x1, y1, x2, y2, x, y);

        return (area == area1 + area2 + area3);
    }

    private static double triangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs(x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2)) / 2.0;
    }

    private boolean inCircle(int x, float y) {
        double centerX = 300;
        double centerY = 300;
        double radius = 100;
        double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        double radiusSquared = Math.pow(radius, 2);
        return distanceSquared <= radiusSquared;
    }
}

