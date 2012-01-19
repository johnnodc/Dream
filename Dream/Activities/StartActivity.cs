using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Dream
{
    [Activity(Label = "Start", MainLauncher = true)]
    public class StartActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Start);

            Button button = FindViewById<Button>(Resource.Id.messageButton);
            // Create your application here

            button.Click += (sender, e) => {
                            
                var showMessageActivity = new Intent(this, typeof(ShowMessageActivity));
                showMessageActivity.PutExtra("Start", "Data from FirstActivity");
                StartActivity(showMessageActivity);
            };
        }
    }
}