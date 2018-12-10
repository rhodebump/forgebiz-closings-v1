
<?php


$bootstrap_css = $plugin_url . 'css/bootstrap-4.0.0-dist/css/bootstrap.css';
$main_js = $plugin_url . 'ClosingsApp/war/closingsapp/closingsapp.nocache.js';
$main_css = $plugin_url . 'css/ClosingsApp.css';
$logo = $plugin_url . 'images/forgebiz-logo-forge.png';
$bootstrap_css = $plugin_url . 'css/bootstrap-4.0.0-dist/css/bootstrap.css';


?><!DOCTYPE html>
<html lang="en">
<head>
<meta content="width=device-width,initial-scale=1" name="viewport">
	<link rel="stylesheet" href="<?php echo $bootstrap_css; ?>">
    <link type="text/css" rel="stylesheet" href="<?php echo $main_css; ?>">
   	<script type="text/javascript" language="javascript" src="<?php echo $main_js; ?>"></script>



</head>
<body>
<?php
$nonce = wp_create_nonce( 'wp_rest' );
?>
<script>
var WordpressForgebizSettings = {
  nonce: "<?php echo $nonce; ?>",
  app_mode:"<?php echo $app_mode; ?>",
  rest_end_point:"<?php echo $rest_end_point; ?>",
};
</script>

    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>

       <div class="container">

    <div id="closingsNav"></div>
            <div id="messagesPanel"></div>
       
    <div id="closingsMain"></div>
    </div>
    


</body>
</html>