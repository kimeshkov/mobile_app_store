<!DOCTYPE html>
<html lang="en" ng-app="storeApp">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Shop Homepage - Start Bootstrap Template</title>

        <!-- Bootstrap Core CSS -->
        <link href="static/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="static/css/app.css" rel="stylesheet">

    </head>

    <body ng-controller="MainCtrl as main">
        <!-- Navigation -->
        <nav class="navbar navbar-fixed-top navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Spring Course</a>
                </div>

                <div class="collapse navbar-collapse">
                    <form ng-show="!main.isLoggedIn()" class="navbar-form navbar-left">

                        <div class="form-group">
                            <input type="text" name="username" class="form-control" placeholder="Username"
                                   ng-model="username">
                            <input type="text" name="password" class="form-control" placeholder="Password"
                                   ng-model="password">
                        </div>
                        <button type="submit" class="btn btn-default" ng-click="main.login()">Sign in</button>
                    </form>

                    <ul class="nav navbar-nav navbar-right">
                       <li>
                           <button ng-show="main.isAdmin()" type="button" class="btn btn-default navbar-btn btn-sign-out">Upload</button>
                       </li>

                        <li>
                            <button ng-show="main.isLoggedIn()" type="button" class="btn btn-default navbar-btn btn-sign-out">Sign out</button>
                        </li>
                    </ul>


                </div>
            </div>
        </nav>

        <!-- Page Content -->
        <div class="container">

            <div ng-view=""></div>

            <hr>

            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p>Copyright &copy; Your Website 2016</p>
                    </div>
                </div>
            </footer>
        </div>

        <script src="static/js/head.load.min.js"></script>
        <script src="static/js/boot.js"></script>

    </body>

</html>
