package com.google.zxing.maxicode.decoder;

import android.support.v4.app.FrameMetricsAggregator;
import android.support.v4.view.InputDeviceCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.stetho.server.http.HttpStatus;
import com.google.api.client.http.HttpStatusCodes;
import com.google.zxing.common.BitMatrix;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import coms.mediatek.wearable.WearableManager;

final class BitMatrixParser {
    private static final int[][] BITNR = {new int[]{TinkerReport.KEY_APPLIED_DEXOPT_OTHER, 120, Opcodes.NEG_FLOAT, Opcodes.NOT_LONG, Opcodes.LONG_TO_FLOAT, Opcodes.LONG_TO_INT, Opcodes.DOUBLE_TO_LONG, Opcodes.DOUBLE_TO_INT, Opcodes.SUB_INT, Opcodes.ADD_INT, 151, 150, 157, 156, Opcodes.SHL_LONG, Opcodes.XOR_LONG, Opcodes.DIV_FLOAT, Opcodes.MUL_FLOAT, Opcodes.REM_DOUBLE, Opcodes.DIV_DOUBLE, 181, 180, Opcodes.ADD_LONG_2ADDR, Opcodes.USHR_INT_2ADDR, Opcodes.OR_LONG_2ADDR, Opcodes.AND_LONG_2ADDR, Opcodes.SUB_FLOAT_2ADDR, Opcodes.ADD_FLOAT_2ADDR, -2, -2}, new int[]{123, 122, 129, 128, 135, Opcodes.LONG_TO_DOUBLE, Opcodes.INT_TO_BYTE, Opcodes.DOUBLE_TO_FLOAT, Opcodes.DIV_INT, Opcodes.MUL_INT, 153, 152, Opcodes.REM_LONG, 158, Opcodes.USHR_LONG, Opcodes.SHR_LONG, Opcodes.ADD_DOUBLE, 170, Opcodes.SUB_INT_2ADDR, Opcodes.ADD_INT_2ADDR, 183, 182, Opcodes.MUL_LONG_2ADDR, Opcodes.SUB_LONG_2ADDR, Opcodes.SHL_LONG_2ADDR, Opcodes.XOR_LONG_2ADDR, 201, 200, 816, -3}, new int[]{Opcodes.NEG_LONG, 124, Opcodes.INT_TO_DOUBLE, Opcodes.INT_TO_FLOAT, Opcodes.FLOAT_TO_DOUBLE, Opcodes.FLOAT_TO_LONG, Opcodes.INT_TO_SHORT, Opcodes.INT_TO_CHAR, Opcodes.AND_INT, Opcodes.REM_INT, 155, 154, Opcodes.OR_LONG, Opcodes.AND_LONG, Opcodes.SUB_FLOAT, Opcodes.ADD_FLOAT, Opcodes.MUL_DOUBLE, Opcodes.SUB_DOUBLE, Opcodes.DIV_INT_2ADDR, Opcodes.MUL_INT_2ADDR, Opcodes.SHR_INT_2ADDR, 184, Opcodes.REM_LONG_2ADDR, Opcodes.DIV_LONG_2ADDR, Opcodes.USHR_LONG_2ADDR, Opcodes.SHR_LONG_2ADDR, 203, 202, 818, 817}, new int[]{283, 282, 277, 276, 271, Constants.LANDSCAPE_270, 265, 264, 259, 258, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK, TinkerReport.KEY_LOADED_EXCEPTION_DEX, 247, 246, 241, 240, 235, 234, 229, 228, Opcodes.XOR_INT_LIT8, Opcodes.OR_INT_LIT8, Opcodes.RSUB_INT_LIT8, Opcodes.ADD_INT_LIT8, Opcodes.DIV_INT_LIT16, 210, 205, 204, BaseQuickAdapter.FOOTER_VIEW, -3}, new int[]{285, 284, 279, 278, BaseQuickAdapter.HEADER_VIEW, 272, 267, 266, 261, 260, 255, TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE, 249, 248, 243, 242, 237, 236, 231, 230, Opcodes.SHR_INT_LIT8, Opcodes.SHL_INT_LIT8, Opcodes.DIV_INT_LIT8, Opcodes.MUL_INT_LIT8, Opcodes.AND_INT_LIT16, Opcodes.REM_INT_LIT16, 207, 206, 821, 820}, new int[]{287, 286, 281, 280, 275, 274, 269, 268, 263, 262, InputDeviceCompat.SOURCE_KEYBOARD, 256, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, 250, 245, 244, 239, 238, 233, 232, 227, Opcodes.USHR_INT_LIT8, Opcodes.AND_INT_LIT8, Opcodes.REM_INT_LIT8, Opcodes.XOR_INT_LIT16, Opcodes.OR_INT_LIT16, 209, 208, 822, -3}, new int[]{289, 288, 295, 294, 301, 300, 307, TinkerReport.KEY_LOADED_MISSING_PATCH_INFO, 313, 312, 319, 318, 325, 324, WearableManager.VERSION_331, WearableManager.VERSION_330, 337, 336, 343, 342, 349, 348, TinkerReport.KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL, TinkerReport.KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND, 361, 360, 367, 366, 824, 823}, new int[]{291, 290, 297, 296, 303, 302, TinkerReport.KEY_LOADED_INFO_CORRUPTED, 308, 315, 314, 321, WearableManager.VERSION_38, 327, 326, 333, 332, 339, 338, 345, 344, TinkerReport.KEY_LOADED_PACKAGE_CHECK_DEX_META, TinkerReport.KEY_LOADED_PACKAGE_CHECK_SIGNATURE, TinkerReport.KEY_LOADED_PACKAGE_CHECK_RES_META, TinkerReport.KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND, 363, 362, 369, 368, 825, -3}, new int[]{293, 292, 299, 298, TinkerReport.KEY_LOADED_MISSING_PATCH_FILE, 304, 311, WearableManager.VERSION_35, 317, 316, 323, 322, 329, 328, 335, 334, 341, 340, 347, 346, TinkerReport.KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND, TinkerReport.KEY_LOADED_PACKAGE_CHECK_LIB_META, 359, TinkerReport.KEY_LOADED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT, 365, 364, 371, 370, 827, 826}, new int[]{HttpStatusCodes.STATUS_CODE_CONFLICT, 408, 403, 402, 397, 396, 391, 390, 79, 78, -2, -2, 13, 12, 37, 36, 2, -1, 44, 43, 109, 108, 385, 384, 379, 378, 373, 372, 828, -3}, new int[]{411, 410, HttpStatusCodes.STATUS_CODE_METHOD_NOT_ALLOWED, 404, 399, 398, 393, 392, 81, 80, 40, -2, 15, 14, 39, 38, 3, -1, -1, 45, 111, 110, 387, 386, 381, 380, 375, 374, 830, 829}, new int[]{413, HttpStatusCodes.STATUS_CODE_PRECONDITION_FAILED, 407, 406, 401, 400, 395, 394, 83, 82, 41, -3, -3, -3, -3, -3, 5, 4, 47, 46, 113, 112, 389, 388, 383, 382, 377, 376, 831, -3}, new int[]{415, 414, 421, 420, 427, 426, 103, 102, 55, 54, 16, -3, -3, -3, -3, -3, -3, -3, 20, 19, 85, 84, 433, 432, 439, 438, 445, 444, 833, 832}, new int[]{417, 416, 423, HttpStatusCodes.STATUS_CODE_UNPROCESSABLE_ENTITY, 429, 428, 105, 104, 57, 56, -3, -3, -3, -3, -3, -3, -3, -3, 22, 21, 87, 86, 435, 434, 441, 440, 447, 446, 834, -3}, new int[]{419, 418, 425, 424, 431, 430, 107, 106, 59, 58, -3, -3, -3, -3, -3, -3, -3, -3, -3, 23, 89, 88, 437, 436, 443, 442, 449, 448, 836, 835}, new int[]{481, 480, 475, 474, 469, 468, 48, -2, 30, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, 0, 53, 52, 463, 462, 457, 456, TinkerReport.KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR, TinkerReport.KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR, 837, -3}, new int[]{483, 482, 477, 476, 471, 470, 49, -1, -2, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -2, -1, 465, 464, 459, 458, 453, TinkerReport.KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK, 839, 838}, new int[]{485, 484, 479, 478, 473, 472, 51, 50, 31, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, 1, -2, 42, 467, 466, 461, 460, 455, 454, 840, -3}, new int[]{487, 486, 493, 492, 499, 498, 97, 96, 61, 60, -3, -3, -3, -3, -3, -3, -3, -3, -3, 26, 91, 90, 505, 504, FrameMetricsAggregator.EVERY_DURATION, 510, 517, 516, 842, 841}, new int[]{489, 488, 495, 494, HttpStatus.HTTP_NOT_IMPLEMENTED, 500, 99, 98, 63, 62, -3, -3, -3, -3, -3, -3, -3, -3, 28, 27, 93, 92, 507, 506, InputDeviceCompat.SOURCE_DPAD, 512, 519, 518, 843, -3}, new int[]{491, 490, 497, 496, HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE, HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, 101, 100, 65, 64, 17, -3, -3, -3, -3, -3, -3, -3, 18, 29, 95, 94, 509, 508, 515, 514, 521, 520, 845, 844}, new int[]{559, 558, 553, 552, 547, BaseQuickAdapter.LOADING_VIEW, 541, 540, 73, 72, 32, -3, -3, -3, -3, -3, -3, 10, 67, 66, 115, 114, 535, 534, 529, 528, 523, 522, 846, -3}, new int[]{561, 560, 555, 554, 549, 548, 543, 542, 75, 74, -2, -1, 7, 6, 35, 34, 11, -2, 69, 68, 117, 116, 537, 536, 531, 530, 525, 524, 848, 847}, new int[]{563, 562, 557, 556, 551, 550, 545, 544, 77, 76, -2, 33, 9, 8, 25, 24, -1, -2, 71, 70, 119, 118, 539, 538, 533, 532, 527, 526, 849, -3}, new int[]{565, 564, 571, 570, 577, 576, 583, 582, 589, 588, 595, 594, 601, ServiceErrorCode.YOU_AND_ME_IS_FRIEND, 607, 606, 613, 612, 619, 618, 625, 624, 631, 630, 637, 636, 643, 642, 851, 850}, new int[]{567, 566, 573, 572, 579, 578, 585, 584, 591, 590, 597, 596, 603, 602, 609, 608, 615, 614, 621, 620, 627, 626, 633, 632, 639, 638, 645, 644, 852, -3}, new int[]{569, 568, 575, 574, 581, 580, 587, 586, 593, 592, 599, 598, 605, 604, 611, 610, 617, 616, 623, 622, 629, 628, 635, 634, 641, 640, 647, 646, 854, 853}, new int[]{727, 726, 721, 720, 715, 714, 709, 708, 703, 702, 697, 696, 691, 690, 685, 684, 679, 678, 673, 672, 667, 666, 661, 660, 655, 654, 649, 648, 855, -3}, new int[]{729, 728, 723, 722, 717, 716, 711, 710, 705, 704, 699, 698, 693, 692, 687, 686, 681, 680, 675, 674, 669, 668, 663, 662, 657, 656, 651, 650, 857, 856}, new int[]{731, 730, 725, 724, 719, 718, 713, 712, 707, 706, 701, 700, 695, 694, 689, 688, 683, 682, 677, 676, 671, 670, 665, 664, 659, 658, 653, 652, 858, -3}, new int[]{733, 732, 739, 738, 745, 744, 751, 750, 757, 756, 763, 762, 769, Opcodes.FILL_ARRAY_DATA_PAYLOAD, 775, 774, 781, 780, 787, 786, 793, 792, 799, 798, 805, 804, 811, 810, 860, 859}, new int[]{735, 734, 741, 740, 747, 746, 753, 752, 759, 758, 765, 764, 771, 770, 777, 776, 783, 782, 789, 788, 795, 794, 801, 800, 807, 806, 813, 812, 861, -3}, new int[]{737, 736, 743, 742, 749, 748, 755, 754, 761, 760, 767, 766, 773, 772, 779, 778, 785, 784, 791, 790, 797, 796, 803, 802, 809, 808, 815, 814, 863, 862}};
    private final BitMatrix bitMatrix;

    BitMatrixParser(BitMatrix bitMatrix2) {
        this.bitMatrix = bitMatrix2;
    }

    /* access modifiers changed from: 0000 */
    public byte[] readCodewords() {
        byte[] result = new byte[Opcodes.ADD_INT];
        int height = this.bitMatrix.getHeight();
        int width = this.bitMatrix.getWidth();
        for (int y = 0; y < height; y++) {
            int[] bitnrRow = BITNR[y];
            for (int x = 0; x < width; x++) {
                int bit = bitnrRow[x];
                if (bit >= 0 && this.bitMatrix.get(x, y)) {
                    int i = bit / 6;
                    result[i] = (byte) (result[i] | ((byte) (1 << (5 - (bit % 6)))));
                }
            }
        }
        return result;
    }
}
