package todo.ft;

import lombok.val;
import lombok.var;

import java.util.ServiceLoader;

class ServiceGetter {
    public static final ServicesProvider SERVICES;

    static {
        val loaded = ServiceLoader.load(ServicesProvider.class).iterator();
        SERVICES = loaded.next();
    }
}
