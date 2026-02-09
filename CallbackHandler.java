//回调函数类
public class CallbackHandler {
    private LoginCallback callback;
    private String successful;
    private String return_name;
    public interface LoginCallback {
        void onLoginResult(boolean success);
    }

    public void setLoginCallback(LoginCallback callback) {
        this.callback = callback;
    }

    public void notifyLoginResult(boolean success) {
        if (callback != null) {
            callback.onLoginResult(success);
        }
    }
}
