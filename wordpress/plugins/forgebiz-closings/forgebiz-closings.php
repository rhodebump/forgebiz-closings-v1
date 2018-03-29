<?php
/**
 * Plugin Name: forgebiz Closings
 * Plugin URI: http://forgebiz.com
 * Description: Allows businesses to perform end-of-day cash register closing
 * Version: 1.0
 * Author: Phillip Rhodes "rhodebump"
 * Author URI: http://www.philliprhodes.com
 * License: GPLv2 or later
 */
global $forgebizclosings_db_version;
$forgebizclosings_db_version = '1.2';

function forgebizclosings_get_closing_setting_tablename($wpdb)
{
    return $wpdb->prefix . 'forgebiz_closing_settings';
}

function forgebizclosings_get_closing_tablename($wpdb)
{
    return $wpdb->prefix . 'forgebiz_closing';
}

function forgebizclosings_get_location_tablename($wpdb)
{
    return $wpdb->prefix . 'forgebiz_location';
}

function forgebizclosings_install()
{
    global $wpdb;
    global $forgebizclosings_db_version;
    
    $table_name = forgebizclosings_get_closing_tablename($wpdb);
    
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
		close_10_dollars decimal(15,2) NOT NULL,		
		close_20_dollars decimal(15,2) NOT NULL,					
		close_50_dollars decimal(15,2) NOT NULL,					
		close_100_dollars decimal(15,2) NOT NULL,
		close_cash_total decimal(15,2) NOT NULL,
		closer_name varchar(100) default NULL, 
		closing_date  datetime NOT NULL,
		date_created  datetime NOT NULL,
		deleted  tinyint NOT NULL,
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
		open_cash_total decimal(15,2) NOT NULL,
		opener_name varchar(255) NOT NULL,
		sub_total_sales decimal(15,2) NOT NULL,
		submitted tinyint NOT NULL,
		sales_total decimal(15,2) NOT NULL,
		income_total decimal(15,2) NOT NULL,
		PRIMARY KEY  (id)
	) $charset_collate;";
    
    require_once (ABSPATH . 'wp-admin/includes/upgrade.php');
    dbDelta($sql);
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
    
    $table_name = forgebizclosings_get_closing_setting_tablename($wpdb);
    
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
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
    
    $table_name = forgebizclosings_get_location_tablename($wpdb);
    
    $location_sql = "CREATE TABLE $table_name (
		id mediumint(9) NOT NULL AUTO_INCREMENT,
		location_name varchar(100) NOT NULL,
		notification_email_addresses varchar(1000) NOT NULL,	
		deleted  tinyint NOT NULL DEFAULT 0,	
		PRIMARY KEY  (id)
	) $charset_collate;";
    
    dbDelta($location_sql);
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
    add_option('forgebizclosings_db_version', $forgebizclosings_db_version);
}

function forgebizclosings_uninstall()
{
    global $wpdb;
    
    delete_option('forgebizclosings_db_version');
    $table_name = getLocationTableName($wpdb);
    
    $wpdb->query(sprintf("DROP TABLE IF EXISTS %s", $table_name));
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
    $table_name = forgebizclosings_get_closing_setting_tablename($wpdb);
    
    $wpdb->query(sprintf("DROP TABLE IF EXISTS %s", $table_name));
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
    
    $table_name = forgebizclosings_get_closing_tablename($wpdb);
    $wpdb->query(sprintf("DROP TABLE IF EXISTS %s", $table_name));
    if ($wpdb->last_error) {
        die($wpdb->last_error);
    }
}

function forgebizclosings_install_data()
{
    global $wpdb;
    
    $table_name = forgebizclosings_get_closing_setting_tablename($wpdb);
    
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
        die($wpdb->last_error);
    }
    $table_name = forgebizclosings_get_location_tablename($wpdb);
    $wpdb->insert($table_name, array(
        
        'location_name' => 'Register #1'
    
    ));
}

register_activation_hook(__FILE__, 'forgebizclosings_install');
register_activation_hook(__FILE__, 'forgebizclosings_install_data');
register_uninstall_hook(__FILE__, 'forgebizclosings_uninstall');

class forgebizclosingsApp
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
        $this->api_route = '^api/forgebiz-closings/(.*)/?'; // Matches /api/weather/{position}
        $this->base_href = '/' . basename(dirname(__FILE__)) . '/'; // Matches /wordpress-angular-plugin/
        
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
            'forgebizclosings_api'
        ), 1, 3);
        
        add_action('admin_menu', array(
            $this,
            'forgebizclosings_page_create'
        ), 1, 3);
    }

    function forgebizclosings_page_create()
    {
        $page_title = 'forgebiz closings';
        $menu_title = 'forgebiz closings';
        $capability = 'edit_others_posts';
        $menu_slug = 'forgebiz_closings';
        $function = 'forgebizclosings_page_display';
        $icon_url = '';
        $position = 24;
        
        add_menu_page($page_title, $menu_title, $capability, $menu_slug, $function, $icon_url, $position);
    }

    public function doPageInclude($app_mode)
    {
        $logo = $this->get_url_to_file('images/forgebiz-logo-forge.png');
        $plugin_url = $this->plugin_url;
        $base_href = $this->base_href;
        $page_title = 'forgebiz closings | forgebiz.com';
        include_once ($this->plugin_dir . 'ClosingsAppInclude.php');
        // include_once ($this->plugin_dir . 'DevPage.php');
        exit();
    }

    public function forgebizclosings_api($wp)
    {
        if ($wp->matched_rule !== $this->api_route) {
            return;
        }
        
        $body = $wp->matched_rule;
        
        $body = json_decode($body, true);
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

    public function get_url_to_file($path_to_file)
    {
        $file = $this->plugin_dir . $path_to_file;
        if (! file_exists($file))
            return false;
        $url = $this->plugin_url . $path_to_file;
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
        $logo = $this->get_url_to_file('images/forgebiz-logo-forge.png');
        $plugin_url = $this->plugin_url;
        $base_href = $this->base_href;
        $app_mode = 'main';
        $page_title = 'forgebiz closings | forgebiz.com';
        // Browser caching for our main template
        $ttl = DAY_IN_SECONDS;
        header("Cache-Control: public, max-age=$ttl");
        // Load index view
        include_once ($this->plugin_dir . 'ClosingsApp.php');
        exit();
    }
} // class forgebizclosingsApp
global $forgebizclosingsApp;
$forgebizclosingsApp = new forgebizclosingsApp();

/*
 * The plugin setup allows one to manage settings and locations
 * This requires elevated permissions ie manage_options
 * whereas the other features just require edit_others_posts
 */

add_action('rest_api_init', function () {
    register_rest_route('forgebiz-closings/v1', '/closing-settings/(?P<id>\d+)', array(
        'methods' => 'GET',
        'callback' => 'forgebizclosings_get_closing_settings',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing-settings/(?P<id>\d+)', array(
        'methods' => 'POST',
        'callback' => 'forgebizclosings_save_closing_settings',
        'permission_callback' => function () {
            return current_user_can('manage_options');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing/search', array(
        'methods' => 'GET',
        'callback' => 'forgebizclosings_closings_search',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/closing/save', array(
        'methods' => 'POST',
        'callback' => 'forgebizclosings_closing_save',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
    
    register_rest_route('forgebiz-closings/v1', '/location/save', array(
        'methods' => 'POST',
        'callback' => 'forgebizclosings_location_save',
        'permission_callback' => function () {
            return current_user_can('manage_options');
        }
    ));
    register_rest_route('forgebiz-closings/v1', '/location/search', array(
        'methods' => 'GET',
        'callback' => 'forgebizclosings_location_search',
        'permission_callback' => function () {
            return current_user_can('edit_others_posts');
        }
    ));
});

function forgebizclosings_page_display()
{
    $forgebizclosingsApp = new forgebizclosingsApp();
    $forgebizclosingsApp->doPageInclude('main');
}

function forgebizclosings_location_save($request)
{
    global $wpdb;
    $table_name = forgebizclosings_get_location_tablename($wpdb);
    
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
    
    if ($wpdb->last_error) {
        $last_error = var_export($wpdb->last_error, true);
        return new WP_REST_Response($last_error, 500);
    }
    
    return new WP_REST_Response($data, 200);
}

function forgebizclosings_closing_save($request)
{
    $data = json_decode(file_get_contents("php://input"));
    
    global $wpdb;
    
    $table_name = forgebizclosings_get_closing_tablename($wpdb);
    
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
        'income_6' => $request['income_6'],
        'income_7' => $request['income_7'],
        'income_8' => $request['income_8'],
        'income_9' => $request['income_9'],
        
        'close_1_cent' => $request['close_1_cent'],
        'close_5_cents' => $request['close_5_cents'],
        'close_10_cents' => $request['close_10_cents'],
        'close_25_cents' => $request['close_25_cents'],
        'close_1_dollar' => $request['close_1_dollar'],
        'close_5_dollars' => $request['close_5_dollars'],
        'close_10_dollars' => $request['close_10_dollars'],
        'close_20_dollars' => $request['close_20_dollars'],
        'close_50_dollars' => $request['close_50_dollars'],
        'close_100_dollars' => $request['close_100_dollars'],
        
        'open_1_cent' => $request['open_1_cent'],
        'open_5_cents' => $request['open_5_cents'],
        'open_10_cents' => $request['open_10_cents'],
        'open_25_cents' => $request['open_25_cents'],
        'open_1_dollar' => $request['open_1_dollar'],
        'open_5_dollars' => $request['open_5_dollars'],
        'open_10_dollars' => $request['open_10_dollars'],
        'open_20_dollars' => $request['open_20_dollars'],
        'open_50_dollars' => $request['open_50_dollars'],
        'open_100_dollars' => $request['open_100_dollars'],
        
        'close_cash_total' => $request['close_cash_total'],
        'open_cash_total' => $request['open_cash_total'],
        'difference' => $request['difference'],
        'sales_total' => $request['sales_total'],
        'income_total' => $request['income_total'],
        
        'opener_name' => $request['opener_name'],
        'location_id' => $request['location_id'],
        'notes' => $request['notes'],
        'closer_name' => $request['closer_name'],
        'closing_date' => $request['closing_date'],
        'last_update' => current_time('mysql'),
        
        'submitted' => $request['submitted'],
        'deleted' => $request['deleted']
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
        '%d',
        '%d',
        '%d',
        
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        '%s',
        
        '%d',
        '%d'
    
    );
    
    $id = $request['id'];
    if ($id) {
        $data['ID'] = $request['id'];
        $format[] = '%d';
    } else {
        
        $data['date_created'] = current_time('mysql');
        $format[] = '%s';
    }

    
    $result = $wpdb->replace($table_name, $data, $format);
    // if (true){
    if ($wpdb->last_error) {
        $last_error = var_export($wpdb->last_error, true);
        $last_query = var_export($wpdb->last_query, true);
        
        $debug = array(
            $last_error,
            $last_query,
            $data,
            $format
        );
        
        return new WP_REST_Response($debug, 500);
    }
    
    $submitted = $request['submitted'];
    if ($submitted) {
        
        try {
            $location_id = $request['location_id'];
            $locations = forgebizclosings_get_location_by_id($location_id);
            $location = $locations[0];
            $notification_email_addresses = $location -> notification_email_addresses;
            $email_address_array = explode("\n", $notification_email_addresses);
            $body = forgebizclosings_get_closing_body($data);
            
            foreach ($email_address_array as $email_address) {
                $subject = 'forgebiz closing submit notification';
                $headers = array(
                    'Content-Type: text/html; charset=UTF-8'
                );
                wp_mail($email_address, $subject, $body, $headers);

            }
        } catch (Exception $e) {
            
            return new WP_REST_Response($e, 500);
        }
    }
    
    return new WP_REST_Response($data, 200);
}

function forgebizclosings_get_closing_body($closing)
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

function forgebizclosings_save_closing_settings($request)
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
    $table_name = forgebizclosings_get_closing_setting_tablename($wpdb);
    
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
    
    if ($wpdb->last_error) {
        $last_error = var_export($wpdb->last_error, true);
        return new WP_REST_Response($last_error, 500);
    }
    
    return new WP_REST_Response($data, 200);
}

function forgebizclosings_location_search($request)
{
    global $wpdb;
    
    $table_name = forgebizclosings_get_location_tablename($wpdb);
    
    $query = "
    SELECT $table_name.* 
    FROM $table_name
 ";
    
    $sql[] = " deleted = 0 ";
    
    if (! empty($sql)) {
        $query .= ' WHERE ' . implode(' AND ', $sql);
    }
    
    $query_results = $wpdb->get_results($query, OBJECT);
    if ($wpdb->last_error) {
        $last_error = var_export($wpdb->last_error, true);
        return new WP_REST_Response($last_error, 500);
    }
    
    return new WP_REST_Response($query_results, 200);
}

function forgebizclosings_get_location_by_id($id)
{
    global $wpdb;
    
    $table_name = forgebizclosings_get_location_tablename($wpdb);
    
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

function forgebizclosings_closings_search($request)
{
    global $wpdb;
    
    $table_name = forgebizclosings_get_closing_tablename($wpdb);
    
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
    
    $location_id = $request['location_id'];
    if ($location_id) {
        $sql[] = " location_id = $location_id ";
    }
    $deleted = $request['deleted'];
    if ($deleted) {
        // so need to show deleted and non-deleted, so let's not add a filter
        $sql[] = " deleted = 1 ";
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
    if ($wpdb->last_error) {
        // if (true) {
        $last_error = var_export($wpdb->last_error, true);
        $debug = array(
            $last_error,
            $query,
            $sql
        );
        
        return new WP_REST_Response($debug, 500);
    }
    
    return new WP_REST_Response($query_results, 200);
}

function forgebizclosings_get_closing_settings($request)
{
    global $wpdb;
    // | wp_forgebiz_closing_settings
    $table_name = forgebizclosings_get_closing_setting_tablename($wpdb);
    
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
    
    if ($wpdb->last_error) {
        $last_error = var_export($wpdb->last_error, true);
        return new WP_REST_Response($last_error, 500);
    }
    
    return new WP_REST_Response($results, 200);
}

add_action('wp_dashboard_setup', 'forgebizclosings_dashboard_widgets');

function forgebizclosings_dashboard_widgets()
{
    global $wp_meta_boxes;
    wp_add_dashboard_widget('custom_help_widget', 'forgebiz closings Support', 'forgebizclosings_dashboard_help');
}

function forgebizclosings_dashboard_help()
{
    echo '<p>Welcome to forgebiz closings! Need help? Contact the developer <a href="mailto:phillip@forgebiz.com">here</a>. For more info visit: <a href="http://www.forgebiz.com" target="_blank">forgebiz</a></p>';
}

add_action('admin_menu', 'forgebizclosings_plugin_menu');

function forgebizclosings_plugin_menu()
{
    add_options_page('forgebiz closings Options', 'forgebiz closings', 'manage_options', 'forgebizclosings', 'forgebizclosings_plugin_options');
}

function forgebizclosings_plugin_options()
{
    if (! current_user_can('manage_options')) {
        wp_die(__('You do not have sufficient permissions to access this page.'));
    }
    
    //
    $forgebizclosingsApp = new forgebizclosingsApp();
    $forgebizclosingsApp->doPageInclude('setup');
}

add_action('admin_enqueue_scripts', 'forgebizclosings_css_and_js');

function forgebizclosings_is_admin_page($hook)
{
    if ('settings_page_forgebizclosings' != $hook) {
        return true;
    } else if ('toplevel_page_forgebiz_closings' != $hook) {
        return true;
    } else {
        return false;
    }
}

function forgebizclosings_css_and_js($hook)
{
    
    // $screen = get_current_screen();
    // print_r($screen);
    
    // settings_page_fbc
    if (! forgebizclosings_is_admin_page($hook)) {
        return;
    }
    
    wp_enqueue_style('forgebizclosings_admin_css', plugins_url('css/ClosingsApp.css', __FILE__));
    wp_enqueue_style('forgebizclosings_bootstrap_css', plugins_url('css/bootstrap-4.0.0-dist/css/bootstrap.css', __FILE__));
    wp_enqueue_script('forgebizclosings_js', plugin_dir_url(__FILE__) . 'ClosingsApp/war/closingsapp/closingsapp.nocache.js', array(), '1.0');
}
