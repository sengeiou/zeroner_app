package com.google.android.gms.internal;

final class zzfih {
    static String zzbc(zzfes zzfes) {
        zzfii zzfii = new zzfii(zzfes);
        StringBuilder sb = new StringBuilder(zzfii.size());
        for (int i = 0; i < zzfii.size(); i++) {
            byte zzkn = zzfii.zzkn(i);
            switch (zzkn) {
                case 7:
                    sb.append("\\a");
                    break;
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case 10:
                    sb.append("\\n");
                    break;
                case 11:
                    sb.append("\\v");
                    break;
                case 12:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case 34:
                    sb.append("\\\"");
                    break;
                case 39:
                    sb.append("\\'");
                    break;
                case 92:
                    sb.append("\\\\");
                    break;
                default:
                    if (zzkn >= 32 && zzkn <= 126) {
                        sb.append((char) zzkn);
                        break;
                    } else {
                        sb.append('\\');
                        sb.append((char) (((zzkn >>> 6) & 3) + 48));
                        sb.append((char) (((zzkn >>> 3) & 7) + 48));
                        sb.append((char) ((zzkn & 7) + 48));
                        break;
                    }
            }
        }
        return sb.toString();
    }
}
