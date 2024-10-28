package fr.r0ane.farmerdelightplus.common.client;

import org.lwjgl.opengl.WGLARBExtensionsString;

public class ClientFermentationBarrelData {
    public static int quantityA;
    public static int quantityB;
    public static String type;

    public static void set(int a, int b, String t) {
        ClientFermentationBarrelData.quantityA = a;
        ClientFermentationBarrelData.quantityB = b;
        ClientFermentationBarrelData.type = t;
    }

    public static int getQuantityA() {
        return quantityA;
    }

    public static int getQuantityB() {
        return quantityB;
    }

    public static String getType() {
        return type;
    }
}
