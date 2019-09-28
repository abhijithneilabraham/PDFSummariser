import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_app/pages/login.dart';
import 'package:my_app/pages/signup.dart';

// class MyHomePage extends StatelessWidget {
//   const MyHomePage({Key key}) : super(key: key);

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         centerTitle: true,
//         title: Text('In A NutShell'),
//         actions: <Widget>[
//           IconButton(
//             icon: Icon(Icons.add),
//             onPressed: () {},
//           )
//         ],
//       ),
//       body: Container(),
//     );
//   }
// }

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Change Status Bar Color
    SystemChrome.setSystemUIOverlayStyle(
      SystemUiOverlayStyle(statusBarColor: Colors.blue),
    );

    // final logo = Container(
    //   height: 100.0,
    //   width: 100.0,
    //   // decoration: BoxDecoration(
    //   //   image: DecorationImage(
    //   //     image: AvailableImages.appLogo,
    //   //     fit: BoxFit.cover,
    //   //   ),
    //   // ),
    //   child:Icon(Icons.picture_as_pdf,size: 40,color: Colors.white,),
    // );

    LinearGradient primaryGradient = LinearGradient(
      colors: [Colors.blue[800], Colors.blue[200]],
      stops: [0.0, 1.0],
      begin: Alignment.topCenter,
      end: Alignment.bottomCenter,
    );

    final appName = Column(
      children: <Widget>[
        Text(
          "In A NutShell",
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: Colors.white,
            fontSize: 30.0,
          ),
        ),
        Text(
          "Sample Tag Line",
          style: TextStyle(
              color: Colors.white, fontSize: 18.0, fontWeight: FontWeight.w500),
        )
      ],
    );

    final loginBtn = InkWell(
      onTap: (){
        Navigator.of(context).pushNamed(LoginPage.routeName);
      },
      // onTap: () => Navigator.pushNamed(context, loginViewRoute),
      child: Container(
        height: 60.0,
        width: MediaQuery.of(context).size.width,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(7.0),
          border: Border.all(color: Colors.white),
          color: Colors.transparent,
        ),
        child: Center(
          child: Text(
            'LOG IN',
            style: TextStyle(
              fontWeight: FontWeight.w600,
              fontSize: 20.0,
              color: Colors.white,
            ),
          ),
        ),
      ),
    );

    final registerBtn = Container(
      height: 60.0,
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(7.0),
        border: Border.all(color: Colors.white),
        color: Colors.white,
      ),
      child: RaisedButton(
        elevation: 5.0,
        onPressed: (){Navigator.of(context).pushNamed(RegisterPage.routeName);},
        // onPressed: () => Navigator.pushNamed(context, registerViewRoute),
        color: Colors.white,
        shape: new RoundedRectangleBorder(
          borderRadius: new BorderRadius.circular(7.0),
        ),
        child: Text(
          'SIGN UP',
          style: TextStyle(
            fontWeight: FontWeight.w600,
            fontSize: 20.0,
          ),
        ),
      ),
    );

    final buttons = Padding(
      padding: EdgeInsets.only(
        top: 80.0,
        bottom: 30.0,
        left: 30.0,
        right: 30.0,
      ),
      child: Column(
        children: <Widget>[loginBtn, SizedBox(height: 20.0), registerBtn],
      ),
    );

    return Scaffold(
      body: Container(
        child: Stack(
          children: <Widget>[
            Container(
              padding: EdgeInsets.only(top: 70.0),
              decoration: BoxDecoration(gradient: primaryGradient),
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width,
              child: Column(
                children: <Widget>[ appName, buttons],
              ),
            ),
            // Positioned(
            //   bottom: 0,
            //   child: Padding(
            //     padding: EdgeInsets.only(left: 10.0),
            //     child: Container(
            //       height: 300.0,
            //       width: MediaQuery.of(context).size.width,
            //       decoration: BoxDecoration(
            //         image: DecorationImage(
            //           image: AvailableImages.homePage,
            //           fit: BoxFit.contain,
            //         ),
            //       ),
            //     ),
            //   ),
            // )
          ],
        ),
      ),
    );
  }
}
