using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Java.Net;


namespace Dream
{
    [Activity(Label = "Show Message")]
    public class ShowMessageActivity : Activity
    {

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            // Get our button from the layout resource,
            // and attach an event to it
            Button button = FindViewById<Button>(Resource.Id.myButton);
            EditText edittext = FindViewById<EditText>(Resource.Id.edittext);

            button.Click += delegate
            {

                string formURL = "message?text=" + edittext.Text + "&type=1&timeout=10";

                URL url = new URL(WebAddressBuilder.BasicURL + formURL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.OpenConnection();
                try
                {
                    string result = urlConnection.ResponseMessage;
                    string outputMessage;
                    switch (result)
                    {
                        case "OK":
                            outputMessage = "Message Sent OK";
                            break;
                        default:
                            outputMessage = "Oops: " + result;
                            break;
                    }

                    Toast.MakeText(this, outputMessage, ToastLength.Short).Show();
                }
                finally
                {
                    urlConnection.Disconnect();
                }
            };
        }
    }
}

