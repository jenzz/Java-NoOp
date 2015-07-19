package com.jenzz.noop.processor;

import com.jenzz.noop.processor.exceptions.UnnamedPackageException;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;

import static java.lang.Boolean.FALSE;

final class Utils {

    private Utils() {
        // no instances
    }

    static String packageName(Elements elements, Element type) throws UnnamedPackageException {
        PackageElement pkg = elements.getPackageOf(type);
        if (pkg.isUnnamed()) {
            throw new UnnamedPackageException("The package of " + type.getSimpleName() + " is unnamed");
        }
        return pkg.getQualifiedName().toString();
    }

    static String defaultValue(TypeKind typeKind) {
        switch (typeKind) {
            case BYTE:
                return "(byte) 0";
            case SHORT:
                return "(short) 0";
            case INT:
                return "0";
            case LONG:
                return "0L";
            case FLOAT:
                return "0.0F";
            case DOUBLE:
                return "0.0D";
            case CHAR:
                return "'\u0000'";
            case BOOLEAN:
                return FALSE.toString();
            default:
                return "null";
        }
    }
}
