package co.chen.server.clock.global;

public class TooManyReportsException extends Throwable{

    public TooManyReportsException(String msg){
        super(msg);
    }
}
