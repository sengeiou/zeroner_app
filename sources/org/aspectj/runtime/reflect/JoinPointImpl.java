package org.aspectj.runtime.reflect;

import org.aspectj.lang.JoinPoint.EnclosingStaticPart;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

class JoinPointImpl implements ProceedingJoinPoint {
    Object _this;
    private AroundClosure arc;
    Object[] args;
    StaticPart staticPart;
    Object target;

    static class EnclosingStaticPartImpl extends StaticPartImpl implements EnclosingStaticPart {
        public EnclosingStaticPartImpl(int count, String kind, Signature signature, SourceLocation sourceLocation) {
            super(count, kind, signature, sourceLocation);
        }
    }

    static class StaticPartImpl implements StaticPart {
        private int id;
        String kind;
        Signature signature;
        SourceLocation sourceLocation;

        public StaticPartImpl(int id2, String kind2, Signature signature2, SourceLocation sourceLocation2) {
            this.kind = kind2;
            this.signature = signature2;
            this.sourceLocation = sourceLocation2;
            this.id = id2;
        }

        public int getId() {
            return this.id;
        }

        public String getKind() {
            return this.kind;
        }

        public Signature getSignature() {
            return this.signature;
        }

        public SourceLocation getSourceLocation() {
            return this.sourceLocation;
        }

        /* access modifiers changed from: 0000 */
        public String toString(StringMaker sm) {
            StringBuffer buf = new StringBuffer();
            buf.append(sm.makeKindName(getKind()));
            buf.append("(");
            buf.append(((SignatureImpl) getSignature()).toString(sm));
            buf.append(")");
            return buf.toString();
        }

        public final String toString() {
            return toString(StringMaker.middleStringMaker);
        }

        public final String toShortString() {
            return toString(StringMaker.shortStringMaker);
        }

        public final String toLongString() {
            return toString(StringMaker.longStringMaker);
        }
    }

    public JoinPointImpl(StaticPart staticPart2, Object _this2, Object target2, Object[] args2) {
        this.staticPart = staticPart2;
        this._this = _this2;
        this.target = target2;
        this.args = args2;
    }

    public Object getThis() {
        return this._this;
    }

    public Object getTarget() {
        return this.target;
    }

    public Object[] getArgs() {
        if (this.args == null) {
            this.args = new Object[0];
        }
        Object[] argsCopy = new Object[this.args.length];
        System.arraycopy(this.args, 0, argsCopy, 0, this.args.length);
        return argsCopy;
    }

    public StaticPart getStaticPart() {
        return this.staticPart;
    }

    public String getKind() {
        return this.staticPart.getKind();
    }

    public Signature getSignature() {
        return this.staticPart.getSignature();
    }

    public SourceLocation getSourceLocation() {
        return this.staticPart.getSourceLocation();
    }

    public final String toString() {
        return this.staticPart.toString();
    }

    public final String toShortString() {
        return this.staticPart.toShortString();
    }

    public final String toLongString() {
        return this.staticPart.toLongString();
    }

    public void set$AroundClosure(AroundClosure arc2) {
        this.arc = arc2;
    }

    public Object proceed() throws Throwable {
        if (this.arc == null) {
            return null;
        }
        return this.arc.run(this.arc.getState());
    }

    public Object proceed(Object[] adviceBindings) throws Throwable {
        boolean thisTargetTheSame;
        boolean hasThis;
        boolean bindsThis;
        boolean hasTarget;
        boolean bindsTarget;
        int i;
        int i2;
        int i3;
        char c;
        char c2 = 1;
        if (this.arc == null) {
            return null;
        }
        int flags = this.arc.getFlags();
        if ((1048576 & flags) != 0) {
        }
        if ((65536 & flags) != 0) {
            thisTargetTheSame = true;
        } else {
            thisTargetTheSame = false;
        }
        if ((flags & 4096) != 0) {
            hasThis = true;
        } else {
            hasThis = false;
        }
        if ((flags & 256) != 0) {
            bindsThis = true;
        } else {
            bindsThis = false;
        }
        if ((flags & 16) != 0) {
            hasTarget = true;
        } else {
            hasTarget = false;
        }
        if ((flags & 1) != 0) {
            bindsTarget = true;
        } else {
            bindsTarget = false;
        }
        Object[] state = this.arc.getState();
        int firstArgumentIndexIntoAdviceBindings = 0;
        if (hasThis) {
            i = 1;
        } else {
            i = 0;
        }
        int firstArgumentIndexIntoState = 0 + i;
        if (!hasTarget || thisTargetTheSame) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        int firstArgumentIndexIntoState2 = firstArgumentIndexIntoState + i2;
        if (hasThis && bindsThis) {
            firstArgumentIndexIntoAdviceBindings = 1;
            state[0] = adviceBindings[0];
        }
        if (hasTarget && bindsTarget) {
            if (thisTargetTheSame) {
                firstArgumentIndexIntoAdviceBindings = (bindsThis ? 1 : 0) + 1;
                if (!bindsThis) {
                    c2 = 0;
                }
                state[0] = adviceBindings[c2];
            } else {
                if (hasThis) {
                    i3 = 1;
                } else {
                    i3 = 0;
                }
                firstArgumentIndexIntoAdviceBindings = i3 + 1;
                if (hasThis) {
                    c = 1;
                } else {
                    c = 0;
                }
                if (!hasThis) {
                    c2 = 0;
                }
                state[c] = adviceBindings[c2];
            }
        }
        for (int i4 = firstArgumentIndexIntoAdviceBindings; i4 < adviceBindings.length; i4++) {
            state[(i4 - firstArgumentIndexIntoAdviceBindings) + firstArgumentIndexIntoState2] = adviceBindings[i4];
        }
        return this.arc.run(state);
    }
}
