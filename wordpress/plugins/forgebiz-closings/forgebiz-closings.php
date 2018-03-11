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
$fbc_db_version = '1.1';

function getClosingSettingTableName($wpdb)
{
    return $wpdb->prefix . 'forgebiz_closing_settings';
}

function getClosingTableName($wpdb)
{
    return $wpdb->prefix . 'forgebiz_closing';
}

function getLocationTableName($wpdb)
{
    return $wpdb->prefix . 'forgebiz_location';
}

function fbc_install()
{
    global $wpdb;
    global $fbc_db_version;
    
    $table_name = getClosingTableName($wpdb);
    
    $charset_collate = $wpdb->get_charset_collate();
    
    $sql = "CREATE TABLE $table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		sales_1 decimal(15,2) NOT NULL,
		sales_2 decimal(15,2) NOT NULL,
		sales_3 decimal(15,2) NOT NULL,
		sales_4 decimal(15,2) NOT NULL,
		sales_5 decimal(15,2) NOT NULL,
		sales_6 decimal(15,2) NOT NULL,
		sales_7 decimal(15,2) NOT NULL,
		sales_8 decimal(15,2) NOT NULL,
		sales_9 decimal(15,2) NOT NULL,		
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
		date_created  datetime NOT NULL,
		deleted  bit(1) NOT NULL,
		difference decimal(15,2) NOT NULL,
		gross_sales decimal(15,2) NOT NULL,
		income_1 decimal(15,2) NOT NULL,
		income_2 decimal(15,2) NOT NULL,
		income_3 decimal(15,2) NOT NULL,
		income_4 decimal(15,2) NOT NULL,
		income_5 decimal(15,2) NOT NULL,
		income_6 decimal(15,2) NOT NULL,
		income_7 decimal(15,2) NOT NULL,
		income_8 decimal(15,2) NOT NULL,
		income_9 decimal(15,2) NOT NULL,		
		income_cash_store decimal(15,2) NOT NULL,
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
		sub_total_sales decimal(15,2) NOT NULL,
		submitted bit(1) NOT NULL,
		sales_total decimal(15,2) NOT NULL,
		income_total decimal(15,2) NOT NULL
		PRIMARY KEY  (id)
	) $charset_collate;";
    
    require_once (ABSPATH . 'wp-admin/includes/upgrade.php');
    dbDelta($sql);
    
    $table_name = getClosingSettingTableName($wpdb);
    
    $label_sql = "CREATE TABLE $table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		show_sales_1 TINYINT(1),
		show_sales_2 TINYINT(1),
		show_sales_3 TINYINT(1),
		show_sales_4 TINYINT(1),
		show_sales_5 TINYINT(1),
		show_sales_6 TINYINT(1),
		show_sales_7 TINYINT(1),
		show_sales_8 TINYINT(1),
		show_sales_9 TINYINT(1),
		sales_label_1 varchar(100) NOT NULL,
		sales_label_2 varchar(100) NOT NULL,
		sales_label_3 varchar(100) NOT NULL,
		sales_label_4 varchar(100) NOT NULL,
		sales_label_5 varchar(100) NOT NULL,
		sales_label_6 varchar(100) NOT NULL,
		sales_label_7 varchar(100) NOT NULL,
		sales_label_8 varchar(100) NOT NULL,
		sales_label_9 varchar(100) NOT NULL,				
		show_income_1 TINYINT(1),
		show_income_2 TINYINT(1),
		show_income_3 TINYINT(1),
		show_income_4 TINYINT(1),
		show_income_5 TINYINT(1),
		show_income_6 TINYINT(1),
		show_income_7 TINYINT(1),
		show_income_8 TINYINT(1),
		show_income_9 TINYINT(1),
		income_label_1 varchar(100) NOT NULL,
		income_label_2 varchar(100) NOT NULL,
		income_label_3 varchar(100) NOT NULL,
		income_label_4 varchar(100) NOT NULL,
		income_label_5 varchar(100) NOT NULL,
		income_label_6 varchar(100) NOT NULL,
		income_label_7 varchar(100) NOT NULL,
		income_label_8 varchar(100) NOT NULL,
		income_label_9 varchar(100) NOT NULL,	
		close_total_label varchar(100) NOT NULL,
		closer_name_label varchar(100) NOT NULL,
		difference_label varchar(100) NOT NULL,
		total_sales_label varchar(100) NOT NULL,
		total_income_label varchar(100) NOT NULL,		
		opener_name_label varchar(255) NOT NULL,
		PRIMARY KEY  (id)
	) $charset_collate;";
    
    dbDelta($label_sql);
    
    $table_name = getLocationTableName($wpdb);
    
    $location_sql = "CREATE TABLE $table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		location_name varchar(100) NOT NULL,
		notification_email_addresses varchar(1000) NOT NULL,	
		deleted  bit(1) NOT NULL DEFAULT 0,	
		PRIMARY KEY  (id)
	) $charset_collate;";
    
    dbDelta($location_sql);
    
    add_option('fbc_db_version', $fbc_db_version);
}

function fbc_install_data()
{
    global $wpdb;
    
    $table_name = getClosingSettingTableName($wpdb);
    
    $wpdb->insert($table_name, array(
        
        'show_sales_1' => 1,
        'show_sales_2' => 1,
        'show_sales_3' => 1,
        'show_sales_4' => 1,
        'show_sales_5' => 1,
        'show_sales_6' => 1,
        'show_sales_7' => 1,
        'show_sales_8' => 1,
        'show_sales_9' => 1,
        'sales_label_1' => 'Sales Product #1',
        'sales_label_2' => 'Sales Product #2',
        'sales_label_3' => 'Sales Product #3',
        'sales_label_4' => 'Sales Product #4',
        'sales_label_5' => 'Sales Product #5',
        'sales_label_6' => 'Sales Product #6',
        'sales_label_7' => 'Sales Product #7',
        'sales_label_8' => 'Sales Product #8',
        'sales_label_9' => 'Sales Product #9',
        'show_income_1' => 1,
        'show_income_2' => 1,
        'show_income_3' => 1,
        'show_income_4' => 1,
        'show_income_5' => 1,
        'show_income_6' => 1,
        'show_income_7' => 1,
        'show_income_8' => 1,
        'show_income_9' => 1,
        'income_label_1' => 'Income #1',
        'income_label_2' => 'Income #2',
        'income_label_3' => 'Income #3',
        'income_label_4' => 'Income #4',
        'income_label_5' => 'Income #5',
        'income_label_6' => 'Income #6',
        'income_label_7' => 'Income #7',
        'income_label_8' => 'Income #8',
        'income_label_9' => 'Income #9',
        'close_total_label' => 'close_total_label',
        'closer_name_label' => 'closer_name_label',
        'difference_label' => 'difference_label',
        'total_income_label' => 'total_income_label',
        'opener_name_label' => 'opener_name_label'
    
    ));
    
    if ($wpdb->last_error) {
        die('error=' . var_dump($wpdb->last_query) . ',' . var_dump($wpdb->error));
    }
    
    $table_name = getLocationTableName($wpdb);
    $wpdb->insert($table_name, array(
        
        'location_name' => 'Register #1'
    
    ));
}

register_activation_hook(__FILE__, 'fbc_install');
register_activation_hook(__FILE__, 'fbc_install_data');

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
    public function __construct()
    {
        // General
        $this->plugin_dir = plugin_dir_path(__FILE__);
        $this->plugin_url = plugins_url('/', __FILE__);
        $this->versions = array();
        // Routing
        // $this->api_route = '^api/weather/(.*)/?'; // Matches /api/weather/{position}
        $this->api_route = '^api/forgebiz-closings/(.*)/?'; // Matches /api/weather/{position}
        $this->base_href = '/' . basename(dirname(__FILE__)) . '/'; // Matches /wordpress-angular-plugin/
                                                                    
        // echo $this->base_href;
                                                                    // die;
                                                                    // /forgebiz-closings/
        
        add_filter('do_parse_request', array(
            $this,
            'intercept_wp_router'
        ), 1, 3);
        add_filter('rewrite_rules_array', array(
            $this,
            'rewrite_rules'
        ));
        add_filter('query_vars', array(
            $this,
            'query_vars'
        ));
        add_action('wp_loaded', array(
            $this,
            'flush_rewrites'
        ));
        // /add_action( 'parse_request', array( $this, 'weather_api' ), 1, 3 );
        add_action('parse_request', array(
            $this,
            'closings_api'
        ), 1, 3);
    }

    public function closings_api($wp)
    {
        // Weather API
        if ($wp->matched_rule !== $this->api_route) {
            return;
        }
        //
        
        $body = $wp->matched_rule;
        
        $body = json_decode($body, true);
        
        // Errors
        // if ( $status !== 200 )
        // wp_send_json_error( $body );
        // Cache control headers, these will cache the
        // users local weather forecast in the browser
        // for 1 day to reduce
        // API requests.
        $ttl = DAY_IN_SECONDS;
        // ^api/forgebiz-closings/(.*)/?
        header("testing: " . $wp->matched_rule);
        header("Cache-Control: public, max-age=$ttl");
        header("Last-Modified: $last_modified");
        // Success response
        wp_send_json_success($body);
    }

    public function flush_rewrites()
    {
        $rules = get_option('rewrite_rules');
        if (! isset($rules[$this->api_route])) {
            global $wp_rewrite;
            $wp_rewrite->flush_rules();
        }
    }

    // Add rule for /api/weather/{position}
    public function rewrite_rules($rules)
    {
        $rules[$this->api_route] = 'index.php?api_position=$matches[1]';
        return $rules;
    }

    // Adding the id var so that WP recognizes it
    public function query_vars($vars)
    {
        array_push($vars, 'api_position');
        return $vars;
    }

    /**
     * Auto-version Assets
     */
    public function auto_version_file($path_to_file)
    {
        $file = $this->plugin_dir . $path_to_file;
        if (! file_exists($file))
            return false;
        $mtime = filemtime($file);
        $url = $this->plugin_url . $path_to_file . '?v=' . $mtime;
        // Store for Last-Modified headers
        array_push($this->versions, $mtime);
        return $url;
    }

    /**
     * Intercept WP Router
     *
     * Intercept WordPress rewrites and serve a
     * static HTML page for our angular.js app.
     */
    public function intercept_wp_router($continue, WP $wp, $extra_query_vars)
    {
        
        // Conditions for url path
        $url_match = (substr($_SERVER['REQUEST_URI'], 0, strlen($this->base_href)) === $this->base_href);
        
        if (! $url_match)
            return $continue;
        $main_js = $this->auto_version_file('ClosingsApp/war/closingsapp/closingsapp.nocache.js');
        $main_css = $this->auto_version_file('ClosingsApp/war/ClosingsApp.css');
        $plugin_url = $this->plugin_url;
        $base_href = $this->base_href;
        
        $page_title = 'Forgebiz Closings | forgebiz.com';
        // Browser caching for our main template
        $ttl = DAY_IN_SECONDS;
        header("Cache-Control: public, max-age=$ttl");
        // Load index view
        include_once ($this->plugin_dir . 'ClosingsApp/war/ClosingsApp.php');
        exit();
    }
} // class gwtApp
new gwtApp();

add_action('rest_api_init', function () {
    // forgebiz_closing_settings
    register_rest_route('forgebiz-closings/v1', '/closing-settings/(?P<id>\d+)', array(
        'methods' => 'GET',
        'callback' => 'get_closing_settings',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing-settings/(?P<id>\d+)', array(
        'methods' => 'POST',
        'callback' => 'save_closing_settings',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing/search', array(
        'methods' => 'GET',
        'callback' => 'closings_search',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing/save', array(
        'methods' => 'POST',
        'callback' => 'closing_save',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/location/save', array(
        'methods' => 'POST',
        'callback' => 'location_save',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    register_rest_route('forgebiz-closings/v1', '/location/search', array(
        'methods' => 'GET',
        'callback' => 'location_search',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
});

function location_save($request)
{
    // $data = json_decode(file_get_contents("php://input"));
    global $wpdb;
    
    $table_name = getLocationTableName($wpdb);
    
    $data = array(
        'location_name' => $request['location_name'],
        'notification_email_addresses' => $request['notification_email_addresses']
    );
    $format = array(
        
        '%s',
        '%s'
    );
    
    $id = $request['id'];
    if ($id) {
        $data['ID'] = $request['id'];
        $format[] = '%d';
    }
    
    $deleted = $request['deleted'];
    if ($deleted) {
        $data['deleted'] = $deleted;
        $format[] = '%d';
    }
    
    $result = $wpdb->replace($table_name, $data, $format);
    
    if (false === $result) {
        
        $data = $wpdb->last_error;
    }
    
    // $debug = var_export($wpdb->last_query, true);
    
    // if ($wpdb->last_error) {
    // die('error=' . var_dump($wpdb->last_query) . ',' . var_dump($wpdb->error));
    // }
    
    return new WP_REST_Response($data, 200);
}

function closing_save($request)
{
    $data = json_decode(file_get_contents("php://input"));
    
    global $wpdb;
    
    $table_name = getClosingTableName($wpdb);
    
    // wpdb::replace( string $table, array $data, array|string $format = null )
    // wpdb::update( string $table, array $data, array $where, array|string $format = null, array|string $where_format = null )
    
    $data = array(
        'sales_1' => $request['sales_1'],
        'sales_2' => $request['sales_2'],
        'sales_3' => $request['sales_3'],
        'sales_4' => $request['sales_4'],
        'sales_5' => $request['sales_5'],
        'sales_6' => $request['sales_6'],
        'sales_7' => $request['sales_7'],
        'sales_8' => $request['sales_8'],
        'sales_9' => $request['sales_9'],
        'income_1' => $request['income_1'],
        'income_2' => $request['income_2'],
        'income_3' => $request['income_3'],
        'income_4' => $request['income_4'],
        'income_5' => $request['income_5'],
        'income_5' => $request['income_5'],
        'income_7' => $request['income_7'],
        'income_8' => $request['income_8'],
        'income_9' => $request['income_9'],
        
        'submitted' => $request['submitted'],
        'deleted' => $request['deleted'],
        'last_update' => current_time('mysql')
    );
    
    $format = array(
        
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        
        '%d',
        '%d',
        '%s'
    
    );
    
    $id = $request['id'];
    if ($id) {
        // $data[] = 'ID' => $request['id'];
        $data['ID'] = $request['id'];
        $format[] = '%d';
    } else {

        $data['date_created'] = current_time('mysql');
        $format[] = '%s';
    }
    
    $result = $wpdb->replace($table_name, $data, $format);
    $submitted = $request['submitted'];
    if ($submitted) {
        
        // need to send notifications
        // lookup location
        $locations = get_location_by_id($location_id);
        $location = $locations[0];
        $notification_email_addresses = $location['notification_email_addresses'];
        $email_address_array = explode("\n", $notification_email_addresses);
        $body = get_closing_body($data);
        
        foreach ($email_address_array as $email_address) {
            $subject = 'forgebiz closing submit notification';
            $headers = array(
                'Content-Type: text/html; charset=UTF-8'
            );
            wp_mail($email_address, $subject, $body, $headers);
        }
    }
    

    
    // echo $wpdb->last_error;
    // die();
    
    if (false === $result) {
        
        $data = $wpdb->last_error;
    }
    
    $debug = var_export($wpdb->last_query, true);
    
    // if ($wpdb->last_error) {
    // die('error=' . var_dump($wpdb->last_query) . ',' . var_dump($wpdb->error));
    // }
    
    return new WP_REST_Response($debug, 200);
}

function get_closing_body($closing)
{
    $body = "<h1>Closing details</h1>";
    $body .= "<table>";
   

    $keys = array_keys($closing);
    
    foreach ($keys as $key) {
        $keyvalue = $closing[$key];
        $body .= "<tr>";
        $body .= "<td>";
        $body .= $key;
        $body .= "</td>";
        $body .= "<td>";
        $body .= $keyvalue;
        $body .= "</td>";
        $body .= "<tr>";
    }
    $body .= "</table>";
    
    return $body;
}

function save_closing_settings($request)
{
    
    /*
     * https://developer.wordpress.org/rest-api/extending-the-rest-api/adding-custom-endpoints/
     *
     * If the request has the Content-type: application/json header set and valid JSON in the body, get_json_params() will return the parsed JSON body as an associative array.
     */
    $data = json_decode(file_get_contents("php://input"));
    
    global $wpdb;
    // wp_forgebiz_labels
    // $data = array("where" => "do we go");
    $table_name = getClosingSettingTableName($wpdb);
    
    $data = array(
        'show_sales_1' => $request['show_sales_1'],
        'show_sales_2' => $request['show_sales_2'],
        'show_sales_3' => $request['show_sales_3'],
        'show_sales_4' => $request['show_sales_4'],
        'show_sales_5' => $request['show_sales_5'],
        'show_sales_6' => $request['show_sales_6'],
        'show_sales_7' => $request['show_sales_7'],
        'show_sales_8' => $request['show_sales_8'],
        'show_sales_9' => $request['show_sales_9'],
        'sales_label_1' => $request['sales_label_1'],
        'sales_label_2' => $request['sales_label_2'],
        'sales_label_3' => $request['sales_label_3'],
        'sales_label_4' => $request['sales_label_4'],
        'sales_label_5' => $request['sales_label_5'],
        'sales_label_6' => $request['sales_label_6'],
        'sales_label_7' => $request['sales_label_7'],
        'sales_label_8' => $request['sales_label_8'],
        'sales_label_9' => $request['sales_label_7'],
        'show_income_1' => $request['show_income_1'],
        'show_income_2' => $request['show_income_2'],
        'show_income_3' => $request['show_income_3'],
        'show_income_4' => $request['show_income_4'],
        'show_income_5' => $request['show_income_5'],
        'show_income_6' => $request['show_income_6'],
        'show_income_7' => $request['show_income_7'],
        'show_income_8' => $request['show_income_8'],
        'show_income_9' => $request['show_income_9'],
        'income_label_1' => $request['income_label_1'],
        'income_label_2' => $request['income_label_2'],
        'income_label_3' => $request['income_label_3'],
        'income_label_4' => $request['income_label_4'],
        'income_label_5' => $request['income_label_5'],
        'income_label_6' => $request['income_label_6'],
        'income_label_7' => $request['income_label_7'],
        'income_label_8' => $request['income_label_8'],
        'income_label_9' => $request['income_label_9']
    
    );
    
    $format = array(
        
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        '%d',
        
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s'
    );
    
    $id = $request['id'];
    if ($id) {
        $data['ID'] = $request['id'];
        $format[] = '%d';
    }
    
    $result = $wpdb->replace($table_name, $data, $format);
    
    // echo $wpdb->last_error;
    // die();
    
    if (false === $result) {
        
        $data = $wpdb->last_error;
    }
    
    $debug = var_export($wpdb->last_query, true);
    
    // if ($wpdb->last_error) {
    // die('error=' . var_dump($wpdb->last_query) . ',' . var_dump($wpdb->error));
    // }
    
    return new WP_REST_Response($data, 200);
}

function location_search($request)
{
    global $wpdb;
    
    $table_name = getLocationTableName($wpdb);
    
    $query = "
    SELECT $table_name.* 
    FROM $table_name
 ";
    
    $sql[] = " deleted = 0 ";
    
    if (! empty($sql)) {
        $query .= ' WHERE ' . implode(' AND ', $sql);
    }
    
    $query_results = $wpdb->get_results($query, OBJECT);
    
    return new WP_REST_Response($query_results, 200);
}

function get_location_by_id($id)
{
    global $wpdb;
    
    $table_name = getLocationTableName($wpdb);
    
    $query = "
    SELECT $table_name.*
    FROM $table_name
 ";
    
    $sql[] = " id = $id ";
    
    if (! empty($sql)) {
        $query .= ' WHERE ' . implode(' AND ', $sql);
    }
    
    $query_results = $wpdb->get_results($query, OBJECT);
    return $query_results;
}

function closings_search($request)
{
    global $wpdb;
    
    $table_name = getClosingTableName($wpdb);
    
    $query = "
    SELECT $table_name.* 
    FROM $table_name
 ";
    
    $startDate = $request['start_date'];
    
    if ($startDate) {
        $sql[] = " closing_date >= '$startDate' ";
    }
    
    $endDate = $request['end_date'];
    if ($endDate) {
        $sql[] = " closing_date <= '$endDate' ";
    }
    
    if (!$startDate && !$enddate) {
        //no date parms submitted, let's default to yesterday
      //  $sql[] = " closing_date >= '$startDate' ";
        
    }
    
    $location_name = $request['location_name'];
    if ($location_name) {
        $sql[] = " location_name = '$location_name' ";
    }
    $deleted = $request['deleted'];
    if ($deleted) {
        // so need to show deleted and non-deleted, so let's not add a filter
    } else {
        $sql[] = " deleted = 0 ";
    }
    
    $id = $request['id'];
    if ($id) {
        $sql[] = " id = $id ";
    }
    
    if (! empty($sql)) {
        $query .= ' WHERE ' . implode(' AND ', $sql);
    }
    
    $query_results = $wpdb->get_results($query, OBJECT);
    
    $data = array(
        "querystr" => $querystr
    );
    // data.push();
    
    return new WP_REST_Response($query_results, 200);
}

function get_closing_settings($request)
{
    global $wpdb;
    // | wp_forgebiz_closing_settings
    $table_name = getClosingSettingTableName($wpdb);
    
    $querystr = "
    SELECT $table_name.* 
    FROM $table_name
 ";
    // should only ever be one settings object
    // if there are more, we will have to figure out what we are doing and pass the id
    // WHERE $table_name.ID = ${request['id']}
    
    $results = $wpdb->get_results($querystr, OBJECT);
    
    $data = array(
        "querystr" => $querystr
    );
    
    // echo $wpdb->last_error;
    // die();
    
    return new WP_REST_Response($results, 200);
}
	
	
