package mobo.andro.apps.ohmagiccamera.CollageMaker;

import mobo.andro.apps.ohmagiccamera.R;

public class Constants {
    public static final String BASIC_FILTER_CONFIG = "@adjust brightness 0.5 @adjust contrast 1 @adjust saturation 1 @adjust sharpen 0 @adjust exposure 0";
    public static final String[] EFFECT_CONFIGS = new String[]{"@beautify face 1 720 720", "@beautify face 1 600 900 @adjust lut lut37.png", "@beautify face 1 600 900 @adjust lut lut1.png", "@beautify face 1 600 900 @adjust lut lut2.png", "@beautify face 1 600 900 @adjust lut lut3.png", "@beautify face 1 600 900 @adjust lut lut4.png", "@beautify face 1 600 900 @vigblend mix 10 10 30 255 91 0 1.0 0.5 0.5 3 @curve R(0, 31)(35, 75)(81, 139)(109, 174)(148, 207)(255, 255)G(0, 24)(59, 88)(105, 146)(130, 171)(145, 187)(180, 214)(255, 255)B(0, 96)(63, 130)(103, 157)(169, 194)(255, 255)", "@beautify face 1 600 900 @adjust lut lut6.png", "@beautify face 1 600 900 @adjust lut lut7.png", "@beautify face 1 600 900 @adjust lut lut8.png", "@beautify face 1 600 900 @curve R(0, 0)(71, 74)(164, 165)(255, 255) @pixblend screen 0.94118 0.29 0.29 1 20", "@beautify face 1 600 900 @adjust lut lut10.png", "@beautify face 1 600 900 @curve G(0, 0)(101, 127)(255, 255) @pixblend colordodge 0.937 0.482 0.835 1 20", "@beautify face 1 600 900 @adjust lut lut11.png", "@beautify face 1 600 900 @adjust lut lut13.png", "@beautify face 1 600 900 @adjust lut lut14.png", "@beautify face 1 600 900 @curve R(15, 0)(92, 133)(255, 234)G(0, 20)(105, 128)(255, 255)B(0, 0)(120, 132)(255, 214)", "@beautify face 1 600 900 @adjust hsv -0.4 -0.64 -1.0 -0.4 -0.88 -0.88 @curve R(0, 0)(119, 160)(255, 255)G(0, 0)(83, 65)(163, 170)(255, 255)B(0, 0)(147, 131)(255, 255)", "@beautify face 1 600 900 @adjust lut lut17.png", "@beautify face 1 600 900 @curve B(0, 0)(70, 87)(140, 191)(255, 255) @pixblend pinlight 0.247 0.49 0.894 1 20", "@beautify face 1 600 900 @curve R(4, 35)(65, 82)(117, 148)(153, 208)(206, 255)G(13, 5)(74, 78)(109, 144)(156, 201)(250, 250)B(6, 37)(93, 104)(163, 184)(238, 222)(255, 237) @adjust hsv -0.2 -0.2 -0.44 -0.2 -0.2 -0.2", "@beautify face 1 600 900 @adjust lut lut20.png", "@beautify face 1 600 900 @adjust lut lut21.png", "@beautify face 1 600 900 @adjust lut lut22.png", "@beautify face 1 600 900 @curve R(5, 49)(85, 173)(184, 249)G(23, 35)(65, 76)(129, 145)(255, 199)B(74, 69)(158, 107)(255, 126)", "@beautify face 1 600 900 @adjust lut lut24.png", "@beautify face 1 600 900 @adjust lut lut25.png", "@beautify face 1 600 900 @adjust lut lut26.png", "@beautify face 1 600 900 @adjust lut lut27.png", "@beautify face 1 600 900 @adjust lut lut28.png", "@beautify face 1 600 900 @adjust lut lut29.png", "@beautify face 1 600 900 @adjust lut lut30.png", "@beautify face 1 600 900 @adjust lut lut31.png", "@beautify face 1 600 900 @curve R(2, 2)(16, 30)(72, 112)(135, 185)(252, 255)G(2, 1)(30, 42)(55, 84)(157, 207)(238, 249)B(1, 0)(26, 17)(67, 106)(114, 165)(231, 250)", "@beautify face 1 600 900 @adjust lut lut33.png", "@beautify face 1 600 900 @adjust lut lut34.png", "@beautify face 1 600 900 @adjust lut lut35.png", "@beautify face 1 600 900 @curve G(0, 0)(144, 166)(255, 255) @pixblend screen 0.94118 0.29 0.29 1 20", "#unpack @dynamic wave 1", "@dynamic wave 0.5", "#unpack @style sketch 0.9", "#unpack @krblend sr effil.jpg 100 @adjust lut foggy_night.png", "#unpack @krblend sr eiffel_tower.jpg 100 @adjust lut foggy_night.png", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b1.png 50", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b2.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b3.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b4.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b5.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b6.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b7.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b8.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b9.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b10.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b11.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b12.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b13.png 100", "@beautify face 1 600 900 @adjust lut lut2.png #unpack @krblend sr b14.png 100"};

    public static int filterName(int pos) {
        switch (pos) {
            case 1:
                return R.string.sala;
            case 2:
                return R.string.dawa;
            case 3:
                return R.string.pica;
            case 4:
                return R.string.rouge;
            case 5:
                return R.string.tuse;
            case 6:
                return R.string.versa;
            case 7:
                return R.string.slide;
            case 8:
                return R.string.bluzzer;
            case 9:
                return R.string.crush;
            case 10:
                return R.string.f2;
            case 11:
                return R.string.elset;
            case 12:
                return R.string.s1933;
            case 13:
                return R.string.koralle;
            case 14:
                return R.string.prism;
            case 15:
                return R.string.tri;
            case 16:
                return R.string.x1;
            case 17:
                return R.string.x2;
            case 18:
                return R.string.x3;
            case 19:
                return R.string.x4;
            case 20:
                return R.string.wind;
            case 21:
                return R.string.s1895;
            case 22:
                return R.string.mono;
            case 23:
                return R.string.leaf;
            case 24:
                return R.string.x5;
            case 25:
                return R.string.x6;
            case 26:
                return R.string.x7;
            case 27:
                return R.string.light;
            case 28:
                return R.string.dark;
            case 29:
                return R.string.wow;
            case 30:
                return R.string.lemo;
            case 31:
                return R.string.Newage;
            case 32:
                return R.string.Crescent;
            case 33:
                return R.string.Gloom;
            case 34:
                return R.string.Hippie;
            case 35:
                return R.string.Coffee;
            case 36:
                return R.string.Nostalgia;
            case 37:
                return R.string.filter_sunrise;
            case 38:
                return R.string.Matrix;
            case 39:
                return R.string.Memory;
            case 40:
                return R.string.Noir;
            case 41:
                return R.string.Eiffeln;
            case 42:
                return R.string.Eiffel;
            case 43:
                return R.string.Fresh1;
            case 44:
                return R.string.bolly;
            case 45:
                return R.string.color;
            case 46:
                return R.string.sunset;
            case 47:
                return R.string.music;
            case 48:
                return R.string.star;
            case 49:
                return R.string.rough;
            case 50:
                return R.string.sun;
            case 51:
                return R.string.rangoli;
            case 52:
                return R.string.flash;
            case 53:
                return R.string.bubble;
            case 54:
                return R.string.star12;
            case 55:
                return R.string.x11;
            default:
                return R.string.none;
        }
    }
}
