<?php
error_reporting( E_ALL ); 
ini_set( 'display_errors', 1 ); 
?>

    <link type="text/css" rel="stylesheet" href="<?php echo $main_css; ?>">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script type="text/javascript" language="javascript" src="<?php echo $main_js; ?>"></script>



<?php
$nonce = wp_create_nonce( 'wp_rest' );
?>
<script>

var WordpressForgebizSettings = {
  nonce: "<?php echo $nonce; ?>",
  app_mode:"<?php echo $app_mode; ?>",
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
<img src="<?php echo $logo; ?>"/>
    <div id="closingsNav"></div>
            <div id="messagesPanel"></div>
       
    <div id="closingsMain"></div>
    </div>
    
