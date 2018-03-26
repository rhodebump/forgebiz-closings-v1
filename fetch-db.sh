#!/bin/bash

mkdir -p  ./target/backups/closings

rsync -avz prhodes@closings.forgebiz.com:/root/backups/closings ./target/backups