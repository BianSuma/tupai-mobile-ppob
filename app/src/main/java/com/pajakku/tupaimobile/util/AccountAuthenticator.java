package com.pajakku.tupaimobile.util;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.pajakku.tupaimobile.activity.LoginActivity;

/**
 * Created by dul on 15/01/19.
 */

public class AccountAuthenticator extends AbstractAccountAuthenticator {
    private Context context;
    public AccountAuthenticator(Context ctx){
        super(ctx);
        context = ctx;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//        intent.putExtra(ADD_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        final AccountManager accountManager = AccountManager.get(context);

        String authToken = accountManager.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            final String password = accountManager.getPassword(account);
            if (password != null) {

                // TODO: @test
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                OkHttpClient client = new OkHttpClient.Builder()
//                        .addInterceptor(interceptor).build();
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .client(client)
//                        .baseUrl(AppConstant.BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                Http httpService = retrofit.create(Http.class);
//                try {
//                    ResponseAuthorize responseAuthorize = httpService.authorize(AppConstant.CLIENT_ID, AppConstant.LOGIN_REDIRECT_URI, AppConstant.LOGIN_STATE, AppConstant.LOGIN_CODE, account.name, password, AppConstant.LOGIN_PASSWORD).execute().body();
//                    ResponseAccessToken responseAccessToken = httpService.accessToken(AppConstant.CLIENT_ID, AppConstant.CLIENT_SECRET, AppConstant.LOGIN_REDIRECT_URI, responseAuthorize.getCode(), responseAuthorize.getState(), AppConstant.LOGIN_AUTHORIZATION_CODE).execute().body();
//                    authToken = responseAccessToken.getAccess_token();
//                }catch (Exception e){
//                    Toast.makeText(context, R.string.auth_toast_failrelogin, Toast.LENGTH_SHORT).show();
//                }

            }
        }

//        if (!TextUtils.isEmpty(authToken)) {
//            final Bundle result = new Bundle();
//            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
//            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.code);
//            result.putString(AppConstant.ITN_LOGINMAIN_BEARERAUTH, AppConstant.HEADER_BEARER_SPACE+authToken);
//            Log.d(AppConstant.LOG_TAG, "------ two =");
//            return result;
//        }

        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return "full";
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
