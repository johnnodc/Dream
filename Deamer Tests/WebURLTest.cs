using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Dream;


namespace Deamer_Tests
{
    [TestClass]
    public class WebURLTest
    {
        [TestMethod]        
        public void Generation_of_the_BasicURL()
        {            
            Assert.IsTrue(WebAddressBuilder.BasicURL.Equals("http://192.168.0.3/web/"));			            
        }
    }
}
