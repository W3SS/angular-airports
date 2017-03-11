var gulp = require('gulp');
var less = require('gulp-less');
var browserSync = require('browser-sync').create();
var header = require('gulp-header');
var cleanCSS = require('gulp-clean-css');
var rename = require("gulp-rename");
var uglify = require('gulp-uglify');
var pkg = require('./package.json');

var vendorDir = 'src/main/resources/static/vendor/';

// Set the banner content
var banner = ['/*!\n',
    ' * <%= pkg.title %> v<%= pkg.version %> (<%= pkg.homepage %>)\n',
    ' * Copyright 2013-' + (new Date()).getFullYear(), ' <%= pkg.author %>\n',
    ' */\n',
    ''
].join('');

// Compile LESS files from /less into /css
gulp.task('less', function() {
    return gulp.src('less/*.less')
        .pipe(less())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(gulp.dest('css'))
        .pipe(browserSync.reload({
            stream: true
        }))
});

// Minify compiled CSS
gulp.task('minify-css', ['less'], function() {
    return gulp.src('css/grayscale.css')
        .pipe(cleanCSS({ compatibility: 'ie8' }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('css'))
        .pipe(browserSync.reload({
            stream: true
        }))
});

// Minify JS
gulp.task('minify-js', function() {
    return gulp.src('js/grayscale.js')
        .pipe(uglify())
        .pipe(header(banner, { pkg: pkg }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('js'))
        .pipe(browserSync.reload({
            stream: true
        }))
});

// Copy vendor libraries from /node_modules and /bower_components into /vendor
gulp.task('copy', function() {
    gulp.src(['bower_components/bootstrap/dist/**/*', 
              '!**/npm.js', 
              '!**/bootstrap-theme.*', 
              '!**/*.map'])
        .pipe(gulp.dest(vendorDir + 'bootstrap'))

    gulp.src(['bower_components/jquery/dist/jquery.js', 
              'bower_components/jquery/dist/jquery.min.js'])
        .pipe(gulp.dest(vendorDir + 'jquery'))

    gulp.src(['node_modules/font-awesome/**',
              '!node_modules/font-awesome/**/*.map',
              '!node_modules/font-awesome/.npmignore',
              '!node_modules/font-awesome/*.txt',
              '!node_modules/font-awesome/*.md',
              '!node_modules/font-awesome/*.json'])
        .pipe(gulp.dest(vendorDir + 'font-awesome'))

    gulp.src(['bower_components/angular/angular.js', 
              'bower_components/angular/angular.min.js', 
              'bower_components/angular/angular-csp.css'])
        .pipe(gulp.dest(vendorDir + 'angular'))

    gulp.src(['bower_components/angular-route/angular-route.js', 
              'bower_components/angular-route/angular-route.min.js', 
              'bower_components/angular-route/angular-csp.css'])
        .pipe(gulp.dest(vendorDir + 'angular-route'))
})

// Run everything
gulp.task('default', ['less', 'minify-css', 'minify-js', 'copy']);

// Configure the browserSync task
gulp.task('browserSync', function() {
    browserSync.init({
        server: {
            baseDir: ''
        },
    })
})

// Dev task with browserSync
gulp.task('dev', ['browserSync', 'less', 'minify-css', 'minify-js'], function() {
    gulp.watch('less/*.less', ['less']);
    gulp.watch('css/*.css', ['minify-css']);
    gulp.watch('js/*.js', ['minify-js']);
    // Reloads the browser whenever HTML or JS files change
    gulp.watch('*.html', browserSync.reload);
    gulp.watch('js/**/*.js', browserSync.reload);
});
