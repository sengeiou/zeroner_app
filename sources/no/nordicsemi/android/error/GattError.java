package no.nordicsemi.android.error;

import android.support.v4.view.InputDeviceCompat;
import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import no.nordicsemi.android.dfu.DfuBaseService;

public class GattError {
    public static String parseConnectionError(int error) {
        switch (error) {
            case 0:
                return "SUCCESS";
            case 1:
                return "GATT CONN L2C FAILURE";
            case 8:
                return "GATT CONN TIMEOUT";
            case 19:
                return "GATT CONN TERMINATE PEER USER";
            case 22:
                return "GATT CONN TERMINATE LOCAL HOST";
            case 34:
                return "GATT CONN LMP TIMEOUT";
            case 62:
                return "GATT CONN FAIL ESTABLISH";
            case Opcodes.LONG_TO_FLOAT /*133*/:
                return "GATT ERROR";
            case 256:
                return "GATT CONN CANCEL ";
            default:
                return "UNKNOWN (" + error + ")";
        }
    }

    public static String parse(int error) {
        switch (error) {
            case 1:
                return "GATT INVALID HANDLE";
            case 2:
                return "GATT READ NOT PERMIT";
            case 3:
                return "GATT WRITE NOT PERMIT";
            case 4:
                return "GATT INVALID PDU";
            case 5:
                return "GATT INSUF AUTHENTICATION";
            case 6:
                return "GATT REQ NOT SUPPORTED";
            case 7:
                return "GATT INVALID OFFSET";
            case 8:
                return "GATT INSUF AUTHORIZATION";
            case 9:
                return "GATT PREPARE Q FULL";
            case 10:
                return "GATT NOT FOUND";
            case 11:
                return "GATT NOT LONG";
            case 12:
                return "GATT INSUF KEY SIZE";
            case 13:
                return "GATT INVALID ATTR LEN";
            case 14:
                return "GATT ERR UNLIKELY";
            case 15:
                return "GATT INSUF ENCRYPTION";
            case 16:
                return "GATT UNSUPPORT GRP TYPE";
            case 17:
                return "GATT INSUF RESOURCE";
            case 34:
                return "GATT CONN LMP TIMEOUT";
            case 58:
                return "GATT CONTROLLER BUSY";
            case 128:
                return "GATT NO RESOURCES";
            case 129:
                return "GATT INTERNAL ERROR";
            case Opcodes.INT_TO_FLOAT /*130*/:
                return "GATT WRONG STATE";
            case Opcodes.INT_TO_DOUBLE /*131*/:
                return "GATT DB FULL";
            case Opcodes.LONG_TO_INT /*132*/:
                return "GATT BUSY";
            case Opcodes.LONG_TO_FLOAT /*133*/:
                return "GATT ERROR";
            case Opcodes.LONG_TO_DOUBLE /*134*/:
                return "GATT CMD STARTED";
            case 135:
                return "GATT ILLEGAL PARAMETER";
            case Opcodes.FLOAT_TO_LONG /*136*/:
                return "GATT PENDING";
            case Opcodes.FLOAT_TO_DOUBLE /*137*/:
                return "GATT AUTH FAIL";
            case Opcodes.DOUBLE_TO_INT /*138*/:
                return "GATT MORE";
            case Opcodes.DOUBLE_TO_LONG /*139*/:
                return "GATT INVALID CFG";
            case Opcodes.DOUBLE_TO_FLOAT /*140*/:
                return "GATT SERVICE STARTED";
            case Opcodes.INT_TO_BYTE /*141*/:
                return "GATT ENCRYPTED NO MITM";
            case Opcodes.INT_TO_CHAR /*142*/:
                return "GATT NOT ENCRYPTED";
            case Opcodes.INT_TO_SHORT /*143*/:
                return "GATT CONGESTED";
            case TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK /*253*/:
                return "GATT CCCD CFG ERROR";
            case TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE /*254*/:
                return "GATT PROCEDURE IN PROGRESS";
            case 255:
                return "GATT VALUE OUT OF RANGE";
            case InputDeviceCompat.SOURCE_KEYBOARD /*257*/:
                return "TOO MANY OPEN CONNECTIONS";
            case 4096:
                return "DFU DEVICE DISCONNECTED";
            case 4097:
                return "DFU FILE NOT FOUND";
            case 4098:
                return "DFU FILE ERROR";
            case 4099:
                return "DFU NOT A VALID HEX FILE";
            case 4100:
                return "DFU IO EXCEPTION";
            case 4101:
                return "DFU SERVICE DISCOVERY NOT STARTED";
            case DfuBaseService.ERROR_SERVICE_NOT_FOUND /*4102*/:
                return "DFU CHARACTERISTICS NOT FOUND";
            case DfuBaseService.ERROR_INVALID_RESPONSE /*4104*/:
                return "DFU INVALID RESPONSE";
            case DfuBaseService.ERROR_FILE_TYPE_UNSUPPORTED /*4105*/:
                return "DFU FILE TYPE NOT SUPPORTED";
            case DfuBaseService.ERROR_BLUETOOTH_DISABLED /*4106*/:
                return "BLUETOOTH ADAPTER DISABLED";
            case DfuBaseService.ERROR_INIT_PACKET_REQUIRED /*4107*/:
                return "DFU INIT PACKET REQUIRED";
            case DfuBaseService.ERROR_FILE_SIZE_INVALID /*4108*/:
                return "DFU INIT PACKET REQUIRED";
            case DfuBaseService.ERROR_CRC_ERROR /*4109*/:
                return "DFU CRC ERROR";
            case DfuBaseService.ERROR_DEVICE_NOT_BONDED /*4110*/:
                return "DFU DEVICE NOT BONDED";
            default:
                return "UNKNOWN (" + error + ")";
        }
    }

    public static String parseDfuRemoteError(int error) {
        switch (error & 3840) {
            case 256:
                return LegacyDfuError.parse(error);
            case 512:
                return SecureDfuError.parse(error);
            case 1024:
                return SecureDfuError.parseExtendedError(error);
            case 2048:
                return SecureDfuError.parseButtonlessError(error);
            default:
                return "UNKNOWN (" + error + ")";
        }
    }
}
