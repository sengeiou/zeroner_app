package com.alibaba.json;

import android.support.v4.view.PointerIconCompat;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.JSONLexer;
import com.alibaba.json.util.TypeUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class JSONReader implements Closeable {
    private JSONStreamContext context;
    private final DefaultJSONParser parser;
    private Reader reader;

    public JSONReader(Reader reader2) {
        this(new JSONLexer(readAll(reader2)));
        this.reader = reader2;
    }

    static String readAll(Reader reader2) {
        StringBuilder buf = new StringBuilder();
        try {
            char[] chars = new char[2048];
            while (true) {
                int len = reader2.read(chars, 0, chars.length);
                if (len < 0) {
                    return buf.toString();
                }
                buf.append(chars, 0, len);
            }
        } catch (Exception ex) {
            throw new JSONException("read string from reader error", ex);
        }
    }

    public JSONReader(JSONLexer lexer) {
        this(new DefaultJSONParser(lexer));
    }

    public JSONReader(DefaultJSONParser parser2) {
        this.parser = parser2;
    }

    public void config(Feature feature, boolean state) {
        this.parser.config(feature, state);
    }

    public void startObject() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, 1001);
        } else {
            startStructure();
            this.context = new JSONStreamContext(this.context, 1001);
        }
        this.parser.accept(12);
    }

    public void endObject() {
        this.parser.accept(13);
        endStructure();
    }

    public void startArray() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, 1004);
        } else {
            startStructure();
            this.context = new JSONStreamContext(this.context, 1004);
        }
        this.parser.accept(14);
    }

    public void endArray() {
        this.parser.accept(15);
        endStructure();
    }

    private void startStructure() {
        switch (this.context.state) {
            case 1001:
            case 1004:
                return;
            case 1002:
                this.parser.accept(17);
                return;
            case PointerIconCompat.TYPE_HELP /*1003*/:
            case 1005:
                this.parser.accept(16);
                return;
            default:
                throw new JSONException("illegal state : " + this.context.state);
        }
    }

    private void endStructure() {
        this.context = this.context.parent;
        if (this.context != null) {
            int newState = -1;
            switch (this.context.state) {
                case 1001:
                case PointerIconCompat.TYPE_HELP /*1003*/:
                    newState = 1002;
                    break;
                case 1002:
                    newState = PointerIconCompat.TYPE_HELP;
                    break;
                case 1004:
                    newState = 1005;
                    break;
            }
            if (newState != -1) {
                this.context.state = newState;
            }
        }
    }

    public boolean hasNext() {
        if (this.context == null) {
            throw new JSONException("context is null");
        }
        int token = this.parser.lexer.token();
        int state = this.context.state;
        switch (state) {
            case 1001:
            case PointerIconCompat.TYPE_HELP /*1003*/:
                if (token == 13) {
                    return false;
                }
                return true;
            case 1004:
            case 1005:
                if (token != 15) {
                    return true;
                }
                return false;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    public int peek() {
        return this.parser.lexer.token();
    }

    public void close() {
        this.parser.lexer.close();
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException ex) {
                throw new JSONException("closed reader error", ex);
            }
        }
    }

    public Integer readInteger() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToInt(object);
    }

    public Long readLong() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToLong(object);
    }

    public String readString() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToString(object);
    }

    public <T> T readObject(TypeReference<T> typeRef) {
        return readObject(typeRef.type);
    }

    public <T> T readObject(Type type) {
        if (this.context == null) {
            return this.parser.parseObject(type);
        }
        readBefore();
        T parseObject = this.parser.parseObject(type);
        readAfter();
        return parseObject;
    }

    public <T> T readObject(Class<T> type) {
        if (this.context == null) {
            return this.parser.parseObject(type);
        }
        readBefore();
        T parseObject = this.parser.parseObject(type);
        readAfter();
        return parseObject;
    }

    public void readObject(Object object) {
        if (this.context == null) {
            this.parser.parseObject(object);
            return;
        }
        readBefore();
        this.parser.parseObject(object);
        readAfter();
    }

    public Object readObject() {
        if (this.context == null) {
            return this.parser.parse();
        }
        readBefore();
        Object parse = this.parser.parse();
        readAfter();
        return parse;
    }

    public Object readObject(Map object) {
        if (this.context == null) {
            return this.parser.parseObject(object);
        }
        readBefore();
        Object parseObject = this.parser.parseObject(object);
        readAfter();
        return parseObject;
    }

    private void readBefore() {
        int state = this.context.state;
        switch (state) {
            case 1001:
            case 1004:
                return;
            case 1002:
                this.parser.accept(17);
                return;
            case PointerIconCompat.TYPE_HELP /*1003*/:
            case 1005:
                this.parser.accept(16);
                return;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    private void readAfter() {
        int state = this.context.state;
        int newStat = -1;
        switch (state) {
            case 1001:
                newStat = 1002;
                break;
            case 1002:
                newStat = PointerIconCompat.TYPE_HELP;
                break;
            case PointerIconCompat.TYPE_HELP /*1003*/:
                newStat = 1002;
                break;
            case 1004:
                newStat = 1005;
                break;
            case 1005:
                break;
            default:
                throw new JSONException("illegal state : " + state);
        }
        if (newStat != -1) {
            this.context.state = newStat;
        }
    }
}
