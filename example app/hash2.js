define
	( function (require, exports, module)
		{
			var $$std$prelude = require("std/prelude.sl");
			var Log = require("std/debuglog.sl"); 
			; 
			var $main = function () 
				{
					var $5 = Log.$print;
					var $4 = sl.hash2.var1[1];
					alert ($4);
					var $main = $5($4);
					return $main 
				}(); 
			; 
			exports.$main = $main
		}
	
	);
