<?php
error_reporting( E_ALL ); 
ini_set( 'display_errors', 1 ); 
?><!DOCTYPE html>
<html lang="en">
<head>
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
};

</script>

    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>

    <h1>Web Application Starter Project</h1>
    <div id="closingsNav"></div>
    
    <div id="closingsMain"></div>
    
    <table align="center">
      <tr>
        <td colspan="2" style="font-weight:bold;">Please enter your name:</td>        
      </tr>
      <tr>
        <td id="nameFieldContainer"></td>
        <td id="sendButtonContainer"></td>
      </tr>
      <tr>
        <td colspan="2" style="color:red;" id="errorLabelContainer"></td>
      </tr>
    </table>



</body>
</html>