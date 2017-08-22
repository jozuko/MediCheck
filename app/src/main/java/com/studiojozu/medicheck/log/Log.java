package com.studiojozu.medicheck.log;

import android.support.annotation.NonNull;

/**
 * ログ出力クラス
 */
@SuppressWarnings("UnusedDeclaration")
public class Log {

    /**
     * ログTAGのベース
     */
    private static final String BASE_TAG = "MediCheck.";

    /**
     * ログ出力元クラス
     */
    @NonNull
    private final Class mSourceClass;

    /**
     * ログ出力元クラスを元にインスタンスを生成するコンストラクタ。
     *
     * @param sourceClass ログ出力元クラス
     */
    public Log(@NonNull Class sourceClass) {
        mSourceClass = sourceClass;
    }

    /**
     * android.util.Log.iを実行する
     *
     * @param message ログメッセージ
     */
    public void i(@NonNull String message) {
        android.util.Log.i(getTag(), message);
    }

    /**
     * android.util.Log.iを実行する
     *
     * @param throwable 例外
     */
    public void i(@NonNull Throwable throwable) {
        android.util.Log.i(getTag(), getThrowableMessage(throwable), throwable);
    }

    /**
     * android.util.Log.wを実行する
     *
     * @param message ログメッセージ
     */
    public void w(@NonNull String message) {
        android.util.Log.w(getTag(), message);
    }

    /**
     * android.util.Log.eを実行する
     *
     * @param throwable 例外
     */
    public void e(@NonNull Throwable throwable) {
        android.util.Log.e(getTag(), getThrowableMessage(throwable), throwable);
    }

    /**
     * ログ出力時のタグを取得する
     *
     * @return ログ出力用TAG
     */
    private String getTag() {
        return BASE_TAG + mSourceClass.getSimpleName();
    }

    /**
     * 例外情報からログ出力用メッセージを取得する
     *
     * @param throwable 例外情報
     * @return 例外情報からログ出力用メッセージ
     */
    private String getThrowableMessage(@NonNull Throwable throwable) {
        String message = throwable.getMessage();
        if (message != null) {
            return message;
        }

        return throwable.toString();
    }

}
