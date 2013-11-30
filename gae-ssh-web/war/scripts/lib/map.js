/**
 * javascript map
 */
function Map() {
	this.elements = new Array();

	/**
	 * 获取MAP元素个数
	 * @return {}
	 */
	this.size = function() {
		return this.elements.length;
	}

	/**
	 * 判断MAP是否为空
	 * @return {}
	 */  
	this.isEmpty = function() {
		return (this.elements.length < 1);
	}

	/**
	 * 删除MAP所有元素
	 */  
	this.clear = function() {
		this.elements = new Array();
	}

	/**
	 * 向MAP中增加元素（key, value)
	 * @param {} _key
	 * @param {} _value
	 */   
	this.put = function(_key, _value) {
		var isContainKey=false;
		for (var i = 0; i < this.elements.length; i++) {
			if (this.elements[i].key == _key) {
				isContainKey=true;
				this.elements[i].value=_value;
				break;
			}
		}
		if(isContainKey==false){
			this.elements.push({
				key : _key,
				value : _value
			});
		}
	}

	/**
	 * 删除指定KEY的元素，成功返回True，失败返回False
	 * @param {} _key
	 * @return {Boolean}
	 */  
	this.remove = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	/**
	 * 获取指定KEY的元素值VALUE，失败返回NULL
	 * @param {} _key
	 * @return {}
	 */  
	this.get = function(_key) {
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	}

	/**
	 * 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
	 * @param {} _index
	 * @return {}
	 */
	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	}

	/**
	 * 判断MAP中是否含有指定KEY的元素
	 * @param {} _key
	 * @return {}
	 */  
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
					break;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	/**
	 * 判断MAP中是否含有指定VALUE的元素 
	 * @param {} _value
	 * @return {}
	 */ 
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
					break;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	}

	/**
	 * 获取MAP中所有VALUE的数组（ARRAY）
	 * @return {}
	 */  
	this.values = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	}

	/**
	 * 获取MAP中所有KEY的数组（ARRAY）
	 * @return {}
	 */  
	this.keys = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	}
}
