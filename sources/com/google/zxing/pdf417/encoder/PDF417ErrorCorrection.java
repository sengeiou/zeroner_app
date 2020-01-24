package com.google.zxing.pdf417.encoder;

import android.support.v4.app.FrameMetricsAggregator;
import android.support.v4.view.InputDeviceCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.api.client.http.HttpStatusCodes;
import com.google.zxing.WriterException;
import com.google.zxing.pdf417.PDF417Common;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import coms.mediatek.wearable.WearableManager;

final class PDF417ErrorCorrection {
    private static final int[][] EC_COEFFICIENTS = {new int[]{27, 917}, new int[]{522, 568, 723, 809}, new int[]{237, 308, 436, 284, 646, 653, 428, 379}, new int[]{274, 562, 232, 755, 599, 524, 801, Opcodes.LONG_TO_INT, 295, 116, 442, 428, 295, 42, Opcodes.ADD_INT_2ADDR, 65}, new int[]{361, 575, 922, 525, Opcodes.ADD_INT_2ADDR, 586, 640, 321, 536, 742, 677, 742, 687, 284, Opcodes.OR_LONG_2ADDR, 517, BaseQuickAdapter.HEADER_VIEW, 494, 263, Opcodes.DIV_INT, 593, 800, 571, WearableManager.VERSION_38, 803, Opcodes.LONG_TO_FLOAT, 231, 390, 685, WearableManager.VERSION_330, 63, 410}, new int[]{539, HttpStatusCodes.STATUS_CODE_UNPROCESSABLE_ENTITY, 6, 93, 862, 771, 453, 106, 610, 287, 107, 505, 733, 877, 381, 612, 723, 476, 462, Opcodes.SUB_DOUBLE, 430, 609, 858, 822, 543, 376, FrameMetricsAggregator.EVERY_DURATION, 400, 672, 762, 283, 184, 440, 35, 519, 31, 460, 594, Opcodes.SHR_INT_LIT8, 535, 517, TinkerReport.KEY_LOADED_PACKAGE_CHECK_LIB_META, 605, 158, 651, 201, 488, HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, 648, 733, 717, 83, 404, 97, 280, 771, 840, 629, 4, 381, 843, 623, 264, 543}, new int[]{521, WearableManager.VERSION_35, 864, 547, 858, 580, 296, 379, 53, 779, 897, 444, 400, 925, 749, 415, 822, 93, Opcodes.RSUB_INT_LIT8, 208, PDF417Common.MAX_CODEWORDS_IN_BARCODE, 244, 583, 620, 246, Opcodes.REM_INT, 447, 631, 292, 908, 490, 704, 516, 258, 457, 907, 594, 723, 674, 292, 272, 96, 684, 432, 686, 606, 860, 569, Opcodes.OR_LONG_2ADDR, Opcodes.DIV_INT_LIT8, 129, Opcodes.USHR_INT_2ADDR, 236, 287, Opcodes.AND_LONG_2ADDR, 775, 278, Opcodes.MUL_DOUBLE, 40, 379, 712, 463, 646, 776, Opcodes.ADD_DOUBLE, 491, 297, 763, 156, 732, 95, Constants.LANDSCAPE_270, 447, 90, 507, 48, 228, 821, 808, 898, 784, 663, 627, 378, 382, 262, 380, 602, 754, 336, 89, 614, 87, 432, 670, 616, 157, 374, 242, 726, ServiceErrorCode.YOU_AND_ME_IS_FRIEND, 269, 375, 898, 845, 454, TinkerReport.KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND, Opcodes.INT_TO_FLOAT, 814, 587, 804, 34, Opcodes.DIV_INT_LIT16, WearableManager.VERSION_330, 539, 297, 827, 865, 37, 517, 834, 315, 550, 86, 801, 4, 108, 539}, new int[]{524, 894, 75, 766, 882, 857, 74, 204, 82, 586, 708, 250, 905, 786, Opcodes.DOUBLE_TO_INT, 720, 858, Opcodes.XOR_LONG_2ADDR, 311, 913, 275, Opcodes.DIV_LONG_2ADDR, 375, 850, 438, 733, Opcodes.XOR_LONG_2ADDR, 280, 201, 280, 828, 757, 710, 814, 919, 89, 68, 569, 11, 204, 796, 605, 540, 913, 801, 700, 799, Opcodes.FLOAT_TO_DOUBLE, 439, 418, 592, 668, TinkerReport.KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND, 859, 370, 694, 325, 240, Opcodes.ADD_INT_LIT8, InputDeviceCompat.SOURCE_KEYBOARD, 284, 549, 209, 884, 315, 70, 329, 793, 490, 274, 877, Opcodes.XOR_LONG, 749, 812, 684, 461, 334, 376, 849, 521, 307, 291, 803, 712, 19, TinkerReport.KEY_LOADED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT, 399, 908, 103, FrameMetricsAggregator.EVERY_DURATION, 51, 8, 517, Opcodes.SHR_INT_LIT8, 289, 470, 637, 731, 66, 255, 917, 269, 463, 830, 730, 433, 848, 585, Opcodes.FLOAT_TO_LONG, 538, 906, 90, 2, 290, 743, Opcodes.SUB_FLOAT_2ADDR, 655, 903, 329, 49, 802, 580, TinkerReport.KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL, 588, Opcodes.SUB_LONG_2ADDR, 462, 10, Opcodes.LONG_TO_DOUBLE, 628, WearableManager.VERSION_38, 479, Opcodes.INT_TO_FLOAT, 739, 71, 263, 318, 374, 601, Opcodes.AND_LONG_2ADDR, 605, Opcodes.INT_TO_CHAR, 673, 687, 234, 722, 384, Opcodes.SUB_INT_2ADDR, 752, 607, 640, 455, Opcodes.OR_LONG_2ADDR, 689, 707, 805, 641, 48, 60, 732, 621, 895, 544, 261, 852, 655, TinkerReport.KEY_LOADED_INFO_CORRUPTED, 697, 755, 756, 60, 231, 773, 434, 421, 726, 528, HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE, 118, 49, 795, 32, Opcodes.ADD_INT, 500, 238, 836, 394, 280, 566, 319, 9, 647, 550, 73, 914, 342, Opcodes.NOT_LONG, 32, 681, WearableManager.VERSION_331, 792, 620, 60, 609, 441, 180, 791, 893, 754, 605, 383, 228, 749, 760, Opcodes.AND_INT_LIT16, 54, 297, Opcodes.LONG_TO_DOUBLE, 54, 834, 299, 922, Opcodes.REM_LONG_2ADDR, 910, 532, 609, 829, Opcodes.MUL_LONG_2ADDR, 20, Opcodes.SUB_FLOAT, 29, 872, 449, 83, 402, 41, 656, 505, 579, 481, Opcodes.MUL_DOUBLE, 404, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, 688, 95, 497, 555, 642, 543, 307, Opcodes.REM_LONG, 924, 558, 648, 55, 497, 10}, new int[]{TinkerReport.KEY_LOADED_PACKAGE_CHECK_LIB_META, 77, 373, 504, 35, 599, 428, 207, HttpStatusCodes.STATUS_CODE_CONFLICT, 574, 118, 498, 285, 380, TinkerReport.KEY_LOADED_PACKAGE_CHECK_SIGNATURE, 492, Opcodes.USHR_LONG_2ADDR, 265, 920, 155, 914, 299, 229, 643, 294, 871, TinkerReport.KEY_LOADED_MISSING_PATCH_INFO, 88, 87, Opcodes.OR_LONG_2ADDR, TinkerReport.KEY_LOADED_PACKAGE_CHECK_LIB_META, 781, 846, 75, 327, 520, 435, 543, 203, 666, 249, 346, 781, 621, 640, 268, 794, 534, 539, 781, 408, 390, 644, 102, 476, 499, 290, 632, 545, 37, 858, 916, 552, 41, 542, 289, 122, 272, 383, 800, 485, 98, 752, 472, 761, 107, 784, 860, 658, 741, 290, 204, 681, 407, 855, 85, 99, 62, 482, 180, 20, 297, TinkerReport.KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR, 593, 913, Opcodes.INT_TO_CHAR, 808, 684, 287, 536, 561, 76, 653, 899, 729, 567, 744, 390, InputDeviceCompat.SOURCE_DPAD, Opcodes.AND_LONG_2ADDR, 516, 258, 240, 518, 794, 395, Opcodes.FILL_ARRAY_DATA_PAYLOAD, 848, 51, 610, 384, Opcodes.MUL_FLOAT, Opcodes.DIV_LONG_2ADDR, 826, 328, 596, 786, 303, 570, 381, 415, 641, 156, 237, 151, 429, 531, 207, 676, 710, 89, Opcodes.MUL_FLOAT, 304, 402, 40, 708, 575, Opcodes.XOR_LONG, 864, 229, 65, 861, 841, 512, Opcodes.SHR_LONG, 477, Opcodes.AND_INT_LIT8, 92, TinkerReport.KEY_LOADED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT, 785, 288, TinkerReport.KEY_LOADED_PACKAGE_CHECK_RES_META, 850, 836, 827, 736, 707, 94, 8, 494, 114, 521, 2, 499, 851, 543, 152, 729, 771, 95, 248, 361, 578, 323, 856, 797, 289, 51, 684, 466, 533, 820, 669, 45, 902, TinkerReport.KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK, Opcodes.SUB_FLOAT, 342, 244, Opcodes.MUL_DOUBLE, 35, 463, 651, 51, 699, 591, TinkerReport.KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK, 578, 37, 124, 298, 332, 552, 43, 427, 119, 662, 777, 475, 850, 764, 364, 578, 911, 283, 711, 472, 420, 245, 288, 594, 394, FrameMetricsAggregator.EVERY_DURATION, 327, 589, 777, 699, 688, 43, 408, 842, 383, 721, 521, 560, 644, 714, 559, 62, Opcodes.SUB_INT, 873, 663, 713, Opcodes.REM_LONG, 672, 729, 624, 59, Opcodes.OR_LONG_2ADDR, 417, 158, 209, 563, 564, 343, 693, 109, 608, 563, 365, 181, 772, 677, WearableManager.VERSION_35, 248, TinkerReport.KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND, 708, 410, 579, 870, 617, 841, 632, 860, 289, 536, 35, 777, 618, 586, 424, 833, 77, 597, 346, 269, 757, 632, 695, 751, WearableManager.VERSION_331, 247, 184, 45, 787, 680, 18, 66, 407, 369, 54, 492, 228, 613, 830, 922, 437, 519, 644, 905, 789, 420, TinkerReport.KEY_LOADED_MISSING_PATCH_FILE, 441, 207, 300, 892, 827, Opcodes.INT_TO_BYTE, 537, 381, 662, InputDeviceCompat.SOURCE_DPAD, 56, TinkerReport.KEY_LOADED_EXCEPTION_DEX, 341, 242, 797, 838, 837, 720, Opcodes.SHL_INT_LIT8, 307, 631, 61, 87, 560, WearableManager.VERSION_35, 756, 665, 397, 808, 851, TinkerReport.KEY_LOADED_INFO_CORRUPTED, 473, 795, 378, 31, 647, 915, 459, 806, 590, 731, 425, Opcodes.ADD_INT_LIT8, 548, 249, 321, 881, 699, 535, 673, 782, 210, 815, 905, 303, 843, 922, 281, 73, 469, 791, 660, Opcodes.XOR_LONG, 498, 308, 155, HttpStatusCodes.STATUS_CODE_UNPROCESSABLE_ENTITY, 907, 817, Opcodes.ADD_LONG_2ADDR, 62, 16, 425, 535, 336, 286, 437, 375, BaseQuickAdapter.HEADER_VIEW, 610, 296, 183, 923, 116, 667, 751, TinkerReport.KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND, 62, 366, 691, 379, 687, 842, 37, TinkerReport.KEY_LOADED_PACKAGE_CHECK_RES_META, 720, 742, WearableManager.VERSION_330, 5, 39, 923, 311, 424, 242, 749, 321, 54, 669, 316, 342, 299, 534, 105, 667, 488, 640, 672, 576, 540, 316, 486, 721, 610, 46, 656, 447, Opcodes.ADD_DOUBLE, 616, 464, Opcodes.DIV_LONG_2ADDR, 531, 297, 321, 762, 752, 533, Opcodes.REM_DOUBLE, Opcodes.LONG_TO_DOUBLE, 14, 381, 433, 717, 45, 111, 20, 596, 284, 736, Opcodes.DOUBLE_TO_INT, 646, 411, 877, 669, Opcodes.INT_TO_BYTE, 919, 45, 780, 407, Opcodes.SHR_LONG, 332, 899, Opcodes.USHR_LONG, 726, ServiceErrorCode.YOU_AND_ME_IS_FRIEND, 325, 498, 655, TinkerReport.KEY_LOADED_PACKAGE_CHECK_RES_META, 752, Opcodes.FILL_ARRAY_DATA_PAYLOAD, Opcodes.XOR_INT_LIT8, 849, 647, 63, WearableManager.VERSION_35, 863, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, 366, 304, 282, 738, 675, 410, 389, 244, 31, TinkerReport.KEY_APPLIED_DEXOPT_OTHER, 303, 263}};

    private PDF417ErrorCorrection() {
    }

    static int getErrorCorrectionCodewordCount(int errorCorrectionLevel) {
        if (errorCorrectionLevel >= 0 && errorCorrectionLevel <= 8) {
            return 1 << (errorCorrectionLevel + 1);
        }
        throw new IllegalArgumentException("Error correction level must be between 0 and 8!");
    }

    static int getRecommendedMinimumErrorCorrectionLevel(int n) throws WriterException {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        } else if (n <= 40) {
            return 2;
        } else {
            if (n <= 160) {
                return 3;
            }
            if (n <= 320) {
                return 4;
            }
            if (n <= 863) {
                return 5;
            }
            throw new WriterException("No recommendation possible");
        }
    }

    static String generateErrorCorrection(CharSequence dataCodewords, int errorCorrectionLevel) {
        int k = getErrorCorrectionCodewordCount(errorCorrectionLevel);
        char[] e = new char[k];
        int sld = dataCodewords.length();
        for (int i = 0; i < sld; i++) {
            int t1 = (dataCodewords.charAt(i) + e[e.length - 1]) % PDF417Common.NUMBER_OF_CODEWORDS;
            for (int j = k - 1; j >= 1; j--) {
                e[j] = (char) ((e[j - 1] + (929 - ((EC_COEFFICIENTS[errorCorrectionLevel][j] * t1) % PDF417Common.NUMBER_OF_CODEWORDS))) % PDF417Common.NUMBER_OF_CODEWORDS);
            }
            e[0] = (char) ((929 - ((EC_COEFFICIENTS[errorCorrectionLevel][0] * t1) % PDF417Common.NUMBER_OF_CODEWORDS)) % PDF417Common.NUMBER_OF_CODEWORDS);
        }
        StringBuilder sb = new StringBuilder(k);
        for (int j2 = k - 1; j2 >= 0; j2--) {
            if (e[j2] != 0) {
                e[j2] = (char) (929 - e[j2]);
            }
            sb.append(e[j2]);
        }
        return sb.toString();
    }
}
