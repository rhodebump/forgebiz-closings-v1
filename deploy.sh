#!/bin/bash
#ant -f ./wordpress/plugins/forgebiz-closings/ClosingsApp/build.xml clean
#ant -f ./wordpress/plugins/forgebiz-closings/ClosingsApp/build.xml build



ant build


rsync -avz ./target/forgebiz-closings.zip  prhodes@demo.forgebiz.com:/home/prhodes/local/libs/apache/forgebiz/www/wp-content/uploads/


#chown -R www-data:www-data forgebiz-closings

#rsync it to the demo box

rsync -avz ./target/forgebiz-closings  prhodes@demo.forgebiz.com:/home/prhodes/local/libs/apache/forgebizdemo/www/wp-content/plugins/

#http://forgebiz.com/wp-content/uploads/2018/03/forgebiz-logo-forge.png

rsync -avz ./target/forgebiz-closings  prhodes@PittsburghColorMeMine.spotmouth.com:/home/prhodes/local/libs/apache/PittsburghColorMeMine/www/wp-content/plugins/

rsync -avz ./closings_v1.sql  prhodes@PittsburghColorMeMine.spotmouth.com:/home/prhodes




