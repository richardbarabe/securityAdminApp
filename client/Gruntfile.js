module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		dirs: {
		    src: 'app',
		    dest: 'app/dist'
		},
		concat: {
			separator:';',
			dist: {
				src:['<%= dirs.src %>/*.js', 
					 '<%= dirs.src %>/admin/**/*.js',
					 '<%= dirs.src %>/security/**/*.js', 
					 '<%= dirs.src %>/commons/**/*.js'],
			    dest: '<%= dirs.dest %>/app.js'
			}
		},
		clean: ['<%= dirs.dest %>'],
		
		connect: {
			server: {
			  options: {
				port: 9001,
				base: 'app',
				keepalive:true
			  }
			}
  }
	});
	
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-connect');
	
	grunt.registerTask('default', ['clean','concat']);
};
