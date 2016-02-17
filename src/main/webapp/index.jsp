<!DOCTYPE html>
<html lang="en" ng-app="mainModule">

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

    <body>
        <!-- Navigation -->
        <nav class="navbar navbar-fixed-top navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Spring Course</a>
                </div>

                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Sign in</button>
                </form>

                <button type="button" class="btn btn-default navbar-btn navbar-right btn-sign-out">Sign out</button>
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

        <!-- jQuery -->
        <script src="static/js/vendor/jquery.js"></script>

        <!-- Angular -->
        <script src="static/js/vendor/angular.js"></script>

        <script src="static/js/vendor/angular-route.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="static/js/vendor/bootstrap.min.js"></script>

        <script src="static/js/app.js"></script>

    </body>

</html>
