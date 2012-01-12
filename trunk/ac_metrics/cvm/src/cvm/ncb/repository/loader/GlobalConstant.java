package cvm.ncb.repository.loader;

public class GlobalConstant {
    public static enum RequestedType {
        Video, Audio, Chat, Emoticon, Voicemail, PC2Phone, Payment, Bandwidth, Lossrate;

        public static RequestedType getRequestType(String name) {
            for (RequestedType reqType : RequestedType.values()) {
                if (reqType.toString().equalsIgnoreCase(name))
                    return reqType;
            }

            return null;
        }
    }

    public static enum OperationType {
        request, selection, reselection
    }

    public static enum PolicyType {
        goalPolicy, actionPolicy
    }

}
