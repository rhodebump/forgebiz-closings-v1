<?php
error_reporting( E_ALL ); 
ini_set( 'display_errors', 1 ); 
?>

<link type="text/css" rel="stylesheet" href="<?php echo $bootstrap_main_css; ?>">

<script type="text/javascript" language="javascript" src="<?php echo $main_js; ?>"></script>

  
  
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

<img src="<?php echo $logo; ?>"/>



    <div id="closingsNav"></div>
            <div id="messagesPanel"></div>
              <div class="container">
    <div id="closingsMain"></div>
    </div>
    


