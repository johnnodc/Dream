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
    public static class WebAddressBuilder
    {
        const string IPNumber = "192.168.0.3";

        private static string basicURL;

        public static string BasicURL
        {
            get
            {
                if (basicURL == null)
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.Append("http://");
                    stringBuilder.Append(IPNumber);
                    stringBuilder.Append("/web/");

                    basicURL = stringBuilder.ToString();                     
                }

                return basicURL;
            }
        }
    }
}

