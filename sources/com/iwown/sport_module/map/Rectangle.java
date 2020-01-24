package com.iwown.sport_module.map;

public class Rectangle {
    private static Rectangle[] exclude = {new Rectangle(25.398623d, 119.921265d, 21.785006d, 122.497559d), new Rectangle(22.284d, 101.8652d, 20.0988d, 106.665d), new Rectangle(21.5422d, 106.4525d, 20.4878d, 108.051d), new Rectangle(55.8175d, 109.0323d, 50.3257d, 119.127d), new Rectangle(55.8175d, 127.4568d, 49.5574d, 137.0227d), new Rectangle(44.8922d, 131.2662d, 42.5692d, 137.0227d)};
    private static Rectangle[] region = {new Rectangle(49.2204d, 79.4462d, 42.8899d, 96.33d), new Rectangle(54.1415d, 109.6872d, 39.3742d, 135.0002d), new Rectangle(42.8899d, 73.1246d, 29.5297d, 124.143255d), new Rectangle(29.5297d, 82.9684d, 26.7186d, 97.0352d), new Rectangle(29.5297d, 97.0253d, 20.414096d, 124.367395d), new Rectangle(20.414096d, 107.975793d, 17.871542d, 111.744104d)};
    public double East;
    public double North;
    public double South;
    public double West;

    public Rectangle(double latitude1, double longitude1, double latitude2, double longitude2) {
        this.West = Math.min(longitude1, longitude2);
        this.North = Math.max(latitude1, latitude2);
        this.East = Math.max(longitude1, longitude2);
        this.South = Math.min(latitude1, latitude2);
    }

    public static boolean IsInsideChina(Gps pos) {
        for (Rectangle InRectangle : region) {
            if (InRectangle(InRectangle, pos)) {
                for (Rectangle InRectangle2 : exclude) {
                    if (InRectangle(InRectangle2, pos)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean InRectangle(Rectangle rect, Gps pos) {
        return rect.West <= pos.getWgLon() && rect.East >= pos.getWgLon() && rect.North >= pos.getWgLat() && rect.South <= pos.getWgLat();
    }
}
