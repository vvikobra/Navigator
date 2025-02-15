package org.example.hashtable;

import java.util.Objects;

public class KeyValue<Key, Value> {
    private Key key;
    private Value value;

    public KeyValue(Key key, Value value) {
        this.setKey(key);
        this.setValue(value);
    }

    public Key getKey() {
        return this.key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getValue() {
        return this.value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", this.getKey(), this.getValue());

    }
}