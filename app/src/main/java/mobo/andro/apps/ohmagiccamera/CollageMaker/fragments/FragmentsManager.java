package mobo.andro.apps.ohmagiccamera.CollageMaker.fragments;

import android.app.Fragment;

public class FragmentsManager {
    public static final int REQUEST_EDITOR = 733;
    public static final int REQUEST_GALLERY_ONCLICK = 533;
    public static final int REQUEST_GALLERY_ONDOUBLECLICK = 633;

    public static Fragment[] getFragmentsByCount(int count) {
        switch (count) {
            case 1:
                return getSingleImageFragment();
            case 2:
                return getDoubleImageFragment();
            case 3:
                return getTripleImageFragment();
            case 4:
                return getFourtImageFragment();
            case 5:
                return getFifthImageFragment();
            case 6:
                return getSixthImageFragment();
            case 7:
                return getSeventhImageFragment();
            case 8:
                return getEighthImageFragment();
            case 9:
                return getNinthImageFragment();
            case 10:
                return getTenthImageFragment();
            default:
                return new Fragment[0];
        }
    }

    private static Fragment[] getSingleImageFragment() {
        return new Fragment[]{CollageOne.newInstance(true)};
    }

    private static Fragment[] getDoubleImageFragment() {
        return new Fragment[]{CollageTwo.newInstance(true), CollageThree.newInstance(true)};
    }

    private static Fragment[] getTripleImageFragment() {
        return new Fragment[]{CollageFour.newInstance(true), CollageFive.newInstance(true), CollageSix.newInstance(true), CollageSeven.newInstance(true), CollageEight.newInstance(true), CollageNine.newInstance(true)};
    }

    private static Fragment[] getFourtImageFragment() {
        return new Fragment[]{CollageTen.newInstance(true), CollageEleven.newInstance(true), CollageTwelve.newInstance(true), CollageThirteen.newInstance(true), CollageFourteen.newInstance(true), CollageSeventeen.newInstance(true), CollageEighteen.newInstance(true), CollageNineteen.newInstance(true), CollageTwenty.newInstance(true), CollageFifty.newInstance(true)};
    }

    private static Fragment[] getFifthImageFragment() {
        return new Fragment[]{CollageFifteen.newInstance(true), CollageSixteen.newInstance(true), CollageTwentyOne.newInstance(true), CollageTwentyTwo.newInstance(true), CollageTwentyThreee.newInstance(true), CollageTwentyFour.newInstance(true), CollageTwentyFive.newInstance(true), CollageTwentySix.newInstance(true), CollageTwentySeven.newInstance(true), CollageTwentyEight.newInstance(true), CollageTwentyNine.newInstance(true), CollageThirty.newInstance(true), CollageThirtyOne.newInstance(true), CollageThirtyTwo.newInstance(true), CollageFourty.newInstance(true), CollageFourtyOne.newInstance(true)};
    }

    private static Fragment[] getSixthImageFragment() {
        return new Fragment[]{CollageThirtyThree.newInstance(true), CollageThirtyFour.newInstance(true), CollageThirtyFive.newInstance(true), CollageThirtySix.newInstance(true), CollageThirtySeven.newInstance(true), CollageThirtyEight.newInstance(true), CollageThirtyNine.newInstance(true)};
    }

    private static Fragment[] getSeventhImageFragment() {
        return new Fragment[]{CollageExtraOne.newInstance(true), CollageExtraTwo.newInstance(true)};
    }

    private static Fragment[] getEighthImageFragment() {
        return new Fragment[]{CollageFourtyTwo.newInstance(true), CollageFourtyThree.newInstance(true)};
    }

    private static Fragment[] getNinthImageFragment() {
        return new Fragment[]{CollageFourtyEight.newInstance(true), CollageFourtyNine.newInstance(true)};
    }

    private static Fragment[] getTenthImageFragment() {
        return new Fragment[]{CollageFourtyFour.newInstance(true), CollageFourtyFive.newInstance(true), CollageFourtySix.newInstance(true), CollageFourtySeven.newInstance(true)};
    }
}
