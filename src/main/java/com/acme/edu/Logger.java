package com.acme.edu;

import java.util.Objects;

public class Logger {

    public static final String typePrimitive = "pimitive: ";
    public static final String typeString = "string: ";
    public static final String typeReference = "reference: ";

    enum TypeCode {
        STRING,
        BYTE,
        INTEGER,
        CHAR,
        OBJECT,
        BOOLEAN,
        NONE
    }

    private static TypeCode prevTypeCode = TypeCode.NONE;

    private static int counter = 0;
    private static int integerSum = 0;
    private static int byteSum = 0;
    private static String prevString = null;

    public static void log(Object message) {
        TypeCode typeCode = getTypeCode(message);

        if (prevTypeCode != TypeCode.NONE && typeCode != prevTypeCode) {
            flush();
        }

        switch (typeCode) {
            case STRING: {
                if(prevString == null) {
                    //counter++;
                } else if (Objects.equals(prevString, message)) {
                    counter++;
                } else {
                    flush();
                }
                prevString = (String)message;
                break;
            }

            case BYTE: {
                byte input = (byte)message;
                byteSum = numberHandler(byteSum, (int)input, Byte.MAX_VALUE, Byte.MIN_VALUE);
                break;
            }
            case INTEGER: {
                integerSum = numberHandler(integerSum, (int)message, Integer.MAX_VALUE, Integer.MIN_VALUE);
                break;
            }
            default: {
                printToConsole(getPrefixType(typeCode) + message);
            }
        }
        prevTypeCode = typeCode;
    }

    public static void flush() {
        switch (prevTypeCode) {
            case STRING: {
                counter++;
                if(counter > 1) {
                    printToConsole(getPrefixType(prevTypeCode) + prevString + " (x" + counter + ")");
                } else {
                    printToConsole(getPrefixType(prevTypeCode) + prevString);
                }
                prevString = null;
                counter = 0;
                break;
            }

            case BYTE: {
                printToConsole(getPrefixType(prevTypeCode) + byteSum);
                byteSum = 0;
                break;
            }
            case INTEGER: {
                printToConsole(getPrefixType(prevTypeCode) + integerSum);
                integerSum = 0;
                break;
            }
        }
    }

    private static void printToConsole(String message) {
        System.out.println(message);
    }

    private static TypeCode getTypeCode(Object message) {
        if (message.getClass() == String.class) {
            return TypeCode.STRING;
        }
        if (message.getClass() == Byte.class) {
            return TypeCode.BYTE;
        }
        if (message.getClass() == Integer.class) {
            return TypeCode.INTEGER;
        }
        if (message.getClass() == Object.class) {
            return TypeCode.OBJECT;
        }
        if (message.getClass() == Boolean.class) {
            return TypeCode.BOOLEAN;
        }
        if (message.getClass() == Character.class) {
            return TypeCode.CHAR;
        }
        return TypeCode.NONE;
    }

    private static String getPrefixType(TypeCode typeCode) {
        switch (typeCode) {
            case STRING: {
                return typeString;
            }

            case BYTE:
            case BOOLEAN:
            case INTEGER: {
                return typePrimitive;
            }

            case CHAR: {
                return typePrimitive;
            }

            case OBJECT: {
                return typeReference;
            }
            default: return null;
        }
    }

    private static long checkOverflow(long result, Integer max, Integer min) {
        if (result > max) {
            return result - max;
        }
        if (result < min) {
            return result + min;
        }
        return result;
    }

    private static int numberHandler(int externalSum, int income, int max, int min) {
        long sum = (long)externalSum + (long)income;
        long result = checkOverflow(sum, max, min);

        if (result > sum) {
            printToConsole(Integer.toString(min));
        }
        if (result < sum) {
            printToConsole(Integer.toString(max));
        }
        return (int)result;
    }
}
