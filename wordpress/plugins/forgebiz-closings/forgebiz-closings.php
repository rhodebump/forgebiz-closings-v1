<?php
/**
* Plugin Name: ForgeBiz Closings
* Plugin URI: http://forgebiz.com
* Description: Allows businesses to perform end-of-day cash register closing
* Version: 1.0 or whatever version of the plugin (pretty self explanatory)
* Author: Phillip Rhodes "rhodebump"
* Author URI: http://PhillipRhodes.com
* License: A "Slug" license name e.g. GPL12
*/

global $fbc_db_version;
$fbc_db_version = '1.0';

function fbc_install() {
	global $wpdb;
	global $fbc_db_version;

	$table_name = $wpdb->prefix . 'forgebiz_closing';
	
	$charset_collate = $wpdb->get_charset_collate();



	$sql = "CREATE TABLE $table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		sales_1 decimal(15,2) NOT NULL,
		sales_2 decimal(15,2) NOT NULL,
		sales_3 decimal(15,2) NOT NULL,
		sales_4 decimal(15,2) NOT NULL,
		sales_5 decimal(15,2) NOT NULL,
		close_1_cent decimal(15,2) NOT NULL,
		close_5_cents decimal(15,2) NOT NULL,	
		close_10_cents decimal(15,2) NOT NULL,	
		close_25_cents decimal(15,2) NOT NULL,		
		close_1_dollar decimal(15,2) NOT NULL,	
		close_5_dollars decimal(15,2) NOT NULL,			
		close_20_dollars decimal(15,2) NOT NULL,					
		close_50_dollars decimal(15,2) NOT NULL,					
		close_100_dollars decimal(15,2) NOT NULL,
		close_total decimal(15,2) NOT NULL,
		closer_name varchar(100) default NULL, 
		closing_date  datetime NOT NULL,
		creditcard_tips decimal(15,2) NOT NULL,
		date_created  datetime NOT NULL,
		deleted  bit(1) NOT NULL,
		difference decimal(15,2) NOT NULL,
		gift_certificates_sold decimal(15,2) NOT NULL,
		gross_sales decimal(15,2) NOT NULL,
		income_1 decimal(15,2) NOT NULL,
		income_cash_store decimal(15,2) NOT NULL,
		income_cash_tips decimal(15,2) NOT NULL,
		income_checks decimal(15,2) NOT NULL,
		income_coupon decimal(15,2) NOT NULL,
		income_credit_card decimal(15,2) NOT NULL,
		income_debits decimal(15,2) NOT NULL,
		income_deposit_redeemed decimal(15,2) NOT NULL,
		income_gift_certificate_redeemed decimal(15,2) NOT NULL,
		income_receipts decimal(15,2) NOT NULL,
		income_total decimal(15,2) NOT NULL,
		last_update datetime default NULL,
		location_id bigint(20) NOT NULL,
		notes varchar(2000) default NULL,
		open_100_dollars decimal(15,2) NOT NULL,
		open_10_cents decimal(15,2) NOT NULL,
		open_10_dollars decimal(15,2) NOT NULL,
		open_1_cent decimal(15,2) NOT NULL,
		open_1_dollar decimal(15,2) NOT NULL,
		open_20_dollars decimal(15,2) NOT NULL,
		open_25_cents decimal(15,2) NOT NULL,
		open_50_dollars decimal(15,2) NOT NULL,
		open_5_cents decimal(15,2) NOT NULL,
		open_5_dollars decimal(15,2) NOT NULL,
		open_total decimal(15,2) NOT NULL,
		opener_name varchar(255) NOT NULL,
		party_deposit decimal(15,2) NOT NULL,
		sales_tax decimal(15,2) NOT NULL,
		sub_total_sales decimal(15,2) NOT NULL,
		submitted bit(1) NOT NULL,
		totala decimal(15,2) NOT NULL,
		totalb decimal(15,2) NOT NULL,
		total_tips decimal(15,2) NOT NULL,
		PRIMARY KEY  (id)
	) $charset_collate;";

	require_once( ABSPATH . 'wp-admin/includes/upgrade.php' );
	dbDelta( $sql );
	//echo $wpdb->last_error; 
	//die();  
	$label_table_name = $wpdb->prefix . 'forgebiz_labels';
	




	$label_sql = "CREATE TABLE $label_table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		sales_1_label varchar(100) NOT NULL,
		sales_2_label varchar(100) NOT NULL,
		sales_3_label varchar(100) NOT NULL,
		sales_4_label varchar(100) NOT NULL,
		sales_5_label varchar(100) NOT NULL,
		close_total_label varchar(100) NOT NULL,
		closer_name_label varchar(100) NOT NULL,
		difference_label varchar(100) NOT NULL,
		gross_sales_label varchar(100) NOT NULL,
		income_1_label varchar(100) NOT NULL,
		income_2_label varchar(100) NOT NULL,		
		opener_name_label varchar(255) NOT NULL,
		PRIMARY KEY  (id)
	) $charset_collate;";


	dbDelta( $label_sql );
	
	
	$location_table_name = $wpdb->prefix . 'forgebiz_locations';
	




	$location_sql = "CREATE TABLE $location_table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		location_name varchar(100) NOT NULL,
		PRIMARY KEY  (id)
	) $charset_collate;";
	
		dbDelta( $location_sql );
	#labels table
	

	add_option( 'fbc_db_version', $fbc_db_version );
}

function fbc_install_data() {
	global $wpdb;
	
	$label_table_name = $wpdb->prefix . 'forgebiz_labels';

	
	$wpdb->insert( 
		$label_table_name, 
		array( 
			'sales_1_label' => "sales 1", 
			'sales_2_label' => "sales 2", 		
			'sales_3_label' => "sales_3", 		
			'sales_4_label' => "sales 4", 					
			'sales_5_label' => "sales 5",
			'close_total_label' => "close_total_label", 		
			'closer_name_label' => "closer_name_label", 		
			'closing_date' => "closing_date", 					
			'difference_label' => "difference_label",
			'gross_sales_label' => "gross_sales_label",
			'income_1_label' => "income_1_label", 		
			'income_2_label' => "income_2_label", 		
			'opener_name_label' => "opener_name_label"		
		) 
	);
}



register_activation_hook( __FILE__, 'fbc_install' );
register_activation_hook( __FILE__, 'fbc_install_data' );










class gwtApp
{	
	public $plugin_dir;
	public $plugin_url;
	public $base_href;
	public $api_route;
	public $versions;
	/**
	 * Setup plugin
	 */
	public function __construct() {
		// General
		$this->plugin_dir = plugin_dir_path( __FILE__ );
		$this->plugin_url = plugins_url( '/', __FILE__ );
		$this->versions = array();
		// Routing
		//$this->api_route = '^api/weather/(.*)/?'; // Matches /api/weather/{position}
		$this->api_route = '^api/forgebiz-closings/(.*)/?'; // Matches /api/weather/{position}
		$this->base_href = '/' . basename( dirname( __FILE__ ) ) . '/'; // Matches /wordpress-angular-plugin/
		
		//echo $this->base_href;
		//die;
		// /forgebiz-closings/
		
		add_filter( 'do_parse_request', array( $this, 'intercept_wp_router' ), 1, 3 );
		add_filter( 'rewrite_rules_array', array( $this, 'rewrite_rules' ) );
		add_filter( 'query_vars', array( $this, 'query_vars' ) );
		add_action( 'wp_loaded', array( $this, 'flush_rewrites' ) );
		///add_action( 'parse_request', array( $this, 'weather_api' ), 1, 3 );
		add_action( 'parse_request', array( $this, 'closings_api' ), 1, 3 );

		
	}

	public function closings_api( $wp ) { 
		// Weather API
		if ( $wp->matched_rule !== $this->api_route ) {
			return;
		}
		// 
		
		$body = $wp->matched_rule;
		

		$body = json_decode( $body, true );
		
		// Errors
		//if ( $status !== 200 ) 
		//	wp_send_json_error( $body );
		// Cache control headers, these will cache the 
		// users local weather forecast in the browser
		// for 1 day to reduce
		//API requests.
		$ttl = DAY_IN_SECONDS;
		//^api/forgebiz-closings/(.*)/?
						header( "testing: "  . $wp->matched_rule);
		header( "Cache-Control: public, max-age=$ttl" );
		header( "Last-Modified: $last_modified" );
		// Success response
		wp_send_json_success( $body );
		
		///wp/v2/posts/<id>
		//   	api/forgebiz-closings/labels/<id>
	}
	




	public function weather_api( $wp ) { 
		// Weather API
		if ( $wp->matched_rule !== $this->api_route )
			return;
		// Validate params
		if ( empty( $wp->query_vars['api_position'] ) ) {
			return wp_send_json_error( 'Missing required position parameters.' );
		}
		// Lookup weather forecast
		$position = esc_attr( $wp->query_vars['api_position'] );
		$coords = explode( ':', $position );
		$lat = round( floatval( $coords[0] ), 4 );
		$long = round( floatval( $coords[1] ), 4 );
		$url = "https://api.weather.gov/points/$lat,$long/forecast";
		$response = wp_remote_get( $url );
		$status = wp_remote_retrieve_response_code( $response );
		$body = wp_remote_retrieve_body( $response );
		$body = json_decode( $body, true );
		
		// Errors
		if ( $status !== 200 ) 
			wp_send_json_error( $body );
		// Cache control headers, these will cache the 
		// users local weather forecast in the browser
		// for 1 day to reduce API requests.
		$ttl = DAY_IN_SECONDS;
		header( "Cache-Control: public, max-age=$ttl" );
		header( "Last-Modified: $last_modified" );

		// Success response
		wp_send_json_success( $body );
	}
	// flush_rules() if our rules are not yet included
	public function flush_rewrites() {
		$rules = get_option( 'rewrite_rules' );
		if ( ! isset( $rules[ $this->api_route ] ) ) {
			global $wp_rewrite;
			$wp_rewrite->flush_rules();
		}
	}
	// Add rule for /api/weather/{position}
	public function rewrite_rules( $rules ) {
		$rules[ $this->api_route ] = 'index.php?api_position=$matches[1]';
		return $rules;
	}
	// Adding the id var so that WP recognizes it
	public function query_vars( $vars ) {
		array_push( $vars, 'api_position' );
		return $vars;
	}
	/**
	 * Auto-version Assets
	 */
	public function auto_version_file( $path_to_file ) {
		$file = $this->plugin_dir . $path_to_file;
		if ( ! file_exists( $file ) ) return false;
		$mtime = filemtime( $file );
		$url = $this->plugin_url . $path_to_file . '?v=' . $mtime;
		// Store for Last-Modified headers
		array_push( $this->versions, $mtime );
		return $url;
	}
	/**
	 * Intercept WP Router
	 *
	 * Intercept WordPress rewrites and serve a 
	 * static HTML page for our angular.js app.
	 */
	public function intercept_wp_router( $continue, WP $wp, $extra_query_vars ) {
		//echo "intercept_wp_router";
//http://localhost:8080/?forgebiz-closings/

		// Conditions for url path
		$url_match = ( substr( $_SERVER['REQUEST_URI'], 0, strlen( $this->base_href ) ) === $this->base_href );
		//echo "server=";
		//echo $_SERVER['REQUEST_URI'] . "\n";
		//echo "base_href=";
		//echo $this->base_href . "\n";
		//echo "url_match $url_match" . "\n";
	
		if ( ! $url_match ) 
			return $continue;
		// Vars for index view
		//		echo "hi";
		//echo die;
		//mywebapp/mywebapp.nocache.js
		//echo "hi";
		//	die;
		$main_js = $this->auto_version_file( 'MyWebApp/war/mywebapp/mywebapp.nocache.js' );
		$main_css = $this->auto_version_file( 'dist/css/main.css' );
		$plugin_url = $this->plugin_url;
		$base_href = $this->base_href;

		$page_title = 'WordPress Angular.js Plugin Demo App | kevinleary.net';
		// Browser caching for our main template
		$ttl = DAY_IN_SECONDS;
		header( "Cache-Control: public, max-age=$ttl" );
		// Load index view
		include_once( $this->plugin_dir . 'MyWebApp/war/MyWebApp.php' );
		exit;
	}
} // class gwtApp
new gwtApp();


add_action( 'rest_api_init', function () {
	register_rest_route( 'forgebiz-closings/v1', '/label/(?P<id>\d+)', array(
		'methods' => 'GET',
		'callback' => 'get_label',
		  'permission_callback' => function () {
      return current_user_can( 'edit_others_posts' );
    }
	) );
	
	register_rest_route( 'forgebiz-closings/v1', '/label/(?P<id>\d+)', array(
		'methods' => 'POST',
		'callback' => 'save_label',
  'permission_callback' => function () {
      return current_user_can( 'edit_others_posts' );
    }		
	) );
	
	
	
} );

	function save_label(  $request ) {
		
		/*
		https://developer.wordpress.org/rest-api/extending-the-rest-api/adding-custom-endpoints/
		
		If the request has the Content-type: application/json header set and valid JSON in the body, get_json_params() will return the parsed JSON body as an associative array.
		*/
  $data = json_decode(file_get_contents("php://input"));
  
 // print_r($request);
  
 // die();
  
  	//$id = $request['id'];
  	//$sales_1_label = $request['sales_1_label'];
  	

	global $wpdb;
		// wp_forgebiz_labels
		//$data = array("where" => "do we go");
		$table_name = $wpdb->prefix . 'forgebiz_labels';
		
			
	
		//https://codeable.io/how-to-import-json-into-wordpress/
$wpdb->update( 
	$table_name , 
	array( 
		//'column1' => 'value1',	// string
		//'column2' => 'value2'	// integer (number) 
		
		
			'sales_1_label' =>  $request['sales_1_label'],
			'sales_2_label' =>  $request['sales_2_label'],
			'sales_3_label' =>  $request['sales_3_label'],
			'sales_4_label' =>  $request['sales_4_label'],		
			'sales_5_label' =>  $request['sales_5_label'],		
			'close_total_label' =>  $request['close_total_label'],		
			'closer_name_label' =>  $request['closer_name_label'],	
			'closing_date' =>  $request['closing_date'],	
			'difference_label' =>  $request['difference_label'],		
			'gross_sales_label' =>  $request['gross_sales_label'],	
			'income_1_label' =>  $request['income_1_label'],	
			'income_2_label' =>  $request['income_2_label'],	
			'income_2_label' =>  $request['income_2_label']
			
			
			
	), 
	array( 'ID' => $request['id'], ), 
	array( 
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s',
		'%s'		
		
		
	), 
	array( '%d' ) 
);

	return new WP_REST_Response(   $data  , 200 );
 
 
	}
	
	function get_label( $request ) {
		
	global $wpdb;
		// wp_forgebiz_labels
		//$data = array("where" => "do we go");
		$table_name = $wpdb->prefix . 'forgebiz_labels';
		
		//$wpdb->prefix . 'forgebiz_labels';
		
 $querystr = "
    SELECT $table_name.* 
    FROM $table_name
    WHERE $table_name.ID = ${request['id']}
 ";
 //$request['id']

 $label_result = $wpdb->get_results($querystr, OBJECT);
// $results=$wpdb->get_results($querystr);
 
 
 $data = array("querystr" => $querystr);
		//data.push();

		return new WP_REST_Response( $label_result, 200 );
		
}
	
	

// SELECT forgebiz_labels.*    FROM forgebiz_labels    WHERE forgebiz_labels.ID = 1 "}

