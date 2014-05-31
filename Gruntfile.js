module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		dirs: {
		    src: 'src/main/webapp/app',
		    dest: 'src/main/webapp/app/dist'
		},
		concat: {
			separator:';',
			dist: {
				src:['<%= dirs.src %>/**/*.js'],
			    dest: '<%= dirs.dest %>/app.js'
			}
		}
	});
	
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.registerTask('default', ['concat']);
};