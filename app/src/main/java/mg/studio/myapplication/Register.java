package mg.studio.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private SessionManager session;
    private ProgressDialog pDialog;
    private String name;
    private String email;
    private String password;

    Feedback feedback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputFullName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);


        // Preparing the Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Session manager
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name = inputFullName.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    // Avoid repeated clicks by disabling the button
                    btnRegister.setClickable(false);
                    //Register the user
                    registerUser(name, email, password);


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Register a new user to the server database
     * @param name     username
     * @param email    email address, which should be unique to the user
     * @param password length should be < 50 characters
     */
    private void registerUser(final String name, final String email,
                              final String password) {

        pDialog.setMessage("Registering ...");
        if (!pDialog.isShowing()) pDialog.show();
        //Todo: Need to check Internet connection
        new DownloadData().execute(name, email, password);


    }

    //Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
    //把账号、密码和账号标识保存到sp里面
    /**
     * 保存账号和密码到SharedPreferences中
     */
    /*saveRegisterInfo(userName, psw);
    //注册成功后把账号传递到LoginActivity.java中
    // 返回值到loginActivity显示
    Intent data = new Intent();
                    data.putExtra("userName", userName);
    setResult(RESULT_OK, data);
    //RESULT_OK为Activity系统常量，状态码为-1，
    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
    RegisterActivity.this.finish();
*/



    class DownloadData extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... strings) {

            /*
            feedback = new Feedback();

            String response = null;
            OutputStreamWriter request = null;
            int parsingFeedback = feedback.FAIL;

*/
            // Variables
            final String BASE_URL = new Config().getRegisterUrl();
            final String NAME = "name";
            final String EMAIL = "email";
            final String PASSWORD = "password";
            final String PARAMS = NAME + "=" + strings[0] + "&" + EMAIL + "=" + strings[1] + "&" + PASSWORD + "=" + strings[2];

/*
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL(BASE_URL);
                connection = (HttpURLConnection) url.openConnection();
                //Set the request method to POST
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                // Timeout for reading InputStream arbitrarily set to 3000ms.
                connection.setReadTimeout(9000);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connection.setConnectTimeout(9000);

                // Output the stream to the server
                request = new OutputStreamWriter(connection.getOutputStream());
                request.write(PARAMS);
                request.flush();
                request.close();

                // Get the inputStream using the same connection
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = readStream(inputStream, 500);
                inputStream.close();

                // Parsing the response
                parsingFeedback = parsingResponse(response);


            } catch (MalformedURLException e) {
                Log.e("TAG", "URL - " + e);
                feedback.setError_message(e.toString());
                return feedback.FAIL;
            } catch (IOException e) {
                Log.e("TAG", "openConnection() - " + e);
                feedback.setError_message(e.toString());
                return feedback.FAIL;
            } finally {
                if (connection != null) // Make sure the connection is not null before disconnecting
                    connection.disconnect();
                Log.d("TAG", "Response " + response);

                return parsingFeedback;
            }

*/
            return 0;
        }


        @Override
        protected void onPostExecute(Integer mFeedback) {
            /*super.onPostExecute(mFeedback);
            if (pDialog.isShowing()) pDialog.dismiss();
            if (mFeedback == feedback.SUCCESS) {
                Intent intent = new Intent(getApplication(), Login.class);
                intent.putExtra("feedback", feedback);
                startActivity(intent);
                finish();
            } else {
                btnRegister.setClickable(true);
                Toast.makeText(getApplication(), feedback.getError_message(), Toast.LENGTH_SHORT).show();
            }
*/

            Toast.makeText(Register.this, "registration success", Toast.LENGTH_SHORT).show();
            //把账号、密码和账号标识保存到sp里面
            /**
             * 保存账号和密码到SharedPreferences中
             */
    saveRegisterInfo(name, password,email);
    //注册成功后把账号传递到LoginActivity.java中
    // 返回值到loginActivity显示
    Intent data = new Intent();
                    data.putExtra("userName", name);
    setResult(RESULT_OK, data);
    //RESULT_OK为Activity系统常量，状态码为-1，
    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
            Intent intent = new Intent(getApplication(), Login.class);
            //intent.putExtra("feedback", feedback);
            startActivity(intent);
    Register.this.finish();


        }

        private void saveRegisterInfo(String userName,String psw,String email){

            //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
            SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
            //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
            SharedPreferences.Editor editor=sp.edit();
            //以用户名为key，密码为value保存在SharedPreferences中
            //key,value,如键值对，editor.putString(用户名，密码）;
            editor.putString( email,psw);
            //提交修改 editor.commit();
            editor.commit();
            String ts = sp.getString(email , "");
            Log.d("TAG","testr "+ts);
        }
        /**
         * Converts the contents of an InputStream to a String.
         */
        String readStream(InputStream stream, int maxReadSize)
                throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] rawBuffer = new char[maxReadSize];
            int readSize;
            StringBuffer buffer = new StringBuffer();
            while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
                if (readSize > maxReadSize) {
                    readSize = maxReadSize;
                }
                buffer.append(rawBuffer, 0, readSize);
                maxReadSize -= readSize;
            }

            Log.d("TAG", buffer.toString());
            return buffer.toString();
        }
    }


    public int parsingResponse(String response) {

        try {
            JSONObject jObj = new JSONObject(response);
            /**
             * If the registration on the server was successful the return should be
             * {"error":false}
             * Else, an object for error message is added
             * Example: {"error":true,"error_msg":"Invalid email format."}
             * Success of the registration can be checked based on the
             * object error, where true refers to the existence of an error
             */
            boolean error = jObj.getBoolean("error");

            if (!error) {
                //No error, return from the server was {"error":false}
                feedback.setName(name);
                return feedback.SUCCESS;
            } else {
                // The return contains error messages
                String errorMsg = jObj.getString("error_msg");
                Log.d("TAG", "errorMsg : " + errorMsg);
                feedback.setError_message(errorMsg);
                return feedback.FAIL;
            }
        } catch (JSONException e) {
            feedback.setError_message(e.toString());
            return feedback.FAIL;
        }

    }

}

