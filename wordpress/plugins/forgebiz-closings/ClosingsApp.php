
<!DOCTYPE html>
<html lang="en">
<head>
<meta content="width=device-width,initial-scale=1" name="viewport">




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