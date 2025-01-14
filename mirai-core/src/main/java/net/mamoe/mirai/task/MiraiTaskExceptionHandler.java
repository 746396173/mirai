package net.mamoe.mirai.task;

@FunctionalInterface
public interface MiraiTaskExceptionHandler {
    void onHandle(Throwable e);

    static MiraiTaskExceptionHandler byDefault(){
        return byPrint();
    }

    static MiraiTaskExceptionHandler byIgnore(){
        return a -> {};
    }

    static MiraiTaskExceptionHandler byPrint(){
        return Throwable::printStackTrace;
    }
}
