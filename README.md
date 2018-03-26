# forgebiz-closings


The build.xml is a file that will copy what is required for the wordpress plugin into the target directory

We need it to be in the structure that it is for development (GWT devmode) and docker for wordpress, also the plugin doesn't need any jar files, etc..  
The plugin zip gets too large with jar files (and they are not even used)



To do the migration from existing closings...
-we need to export the exiting table as a sql export (used phpmyadmin for this)
saved file as closing_v1.sql
run the deploy.sh, this copies the file to new server
on the server, load the table
on the server, run the wp_forgebiz_closing.sql, this copies data from old table to new table

