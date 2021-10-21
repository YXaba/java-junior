package buffer;

import message.Message;

public class ObjectBuffer extends Buffer {

    public ObjectBuffer(String prefix) {
        this.prefix = prefix;
    }

    public void accumulate(Message object) {
        extraOrdinary.add(object.getBody().toString());
    }

}
