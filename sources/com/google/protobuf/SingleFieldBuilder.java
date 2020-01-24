package com.google.protobuf;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.MessageOrBuilder;

public class SingleFieldBuilder<MType extends GeneratedMessage, BType extends Builder, IType extends MessageOrBuilder> implements BuilderParent {
    private BType builder;
    private boolean isClean;
    private MType message;
    private BuilderParent parent;

    public SingleFieldBuilder(MType message2, BuilderParent parent2, boolean isClean2) {
        if (message2 == null) {
            throw new NullPointerException();
        }
        this.message = message2;
        this.parent = parent2;
        this.isClean = isClean2;
    }

    public void dispose() {
        this.parent = null;
    }

    public MType getMessage() {
        if (this.message == null) {
            this.message = (GeneratedMessage) this.builder.buildPartial();
        }
        return this.message;
    }

    public MType build() {
        this.isClean = true;
        return getMessage();
    }

    public BType getBuilder() {
        if (this.builder == null) {
            this.builder = (Builder) this.message.newBuilderForType((BuilderParent) this);
            this.builder.mergeFrom((Message) this.message);
            this.builder.markClean();
        }
        return this.builder;
    }

    public IType getMessageOrBuilder() {
        if (this.builder != null) {
            return this.builder;
        }
        return this.message;
    }

    public SingleFieldBuilder<MType, BType, IType> setMessage(MType message2) {
        if (message2 == null) {
            throw new NullPointerException();
        }
        this.message = message2;
        if (this.builder != null) {
            this.builder.dispose();
            this.builder = null;
        }
        onChanged();
        return this;
    }

    public SingleFieldBuilder<MType, BType, IType> mergeFrom(MType value) {
        if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
            this.message = value;
        } else {
            getBuilder().mergeFrom((Message) value);
        }
        onChanged();
        return this;
    }

    public SingleFieldBuilder<MType, BType, IType> clear() {
        MType defaultInstanceForType;
        if (this.message != null) {
            defaultInstanceForType = this.message.getDefaultInstanceForType();
        } else {
            defaultInstanceForType = this.builder.getDefaultInstanceForType();
        }
        this.message = (GeneratedMessage) defaultInstanceForType;
        if (this.builder != null) {
            this.builder.dispose();
            this.builder = null;
        }
        onChanged();
        return this;
    }

    private void onChanged() {
        if (this.builder != null) {
            this.message = null;
        }
        if (this.isClean && this.parent != null) {
            this.parent.markDirty();
            this.isClean = false;
        }
    }

    public void markDirty() {
        onChanged();
    }
}
