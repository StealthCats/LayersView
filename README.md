# MultiListView
Create MultiLvel For ListView。 dose not limit for level number

ListView 的多层级适配器，可以无限嵌套。

实际上使用的还是ListView， 除了需要重写getCountByPostion和getViewByPosition方法，其余使用和正常使用ListView一样。



 /**
     * 获取对应位置条目展开时的数量
     * @param position 位置信息，etc：position 没有值时，获取第一层的数量， 当position = 2 时，获取的是第一层的第2条展开时的数量，当positoin = 2,3 时，则获取的是第一层第2条展开后的第3条的展开后的数量，以此类推
     * @return
     */
    protected abstract int getCountByPosition(int ... position);
    
   /**
     * 获取对应条目对应的View @link:getCountByPosition
     * @param convertView
     * @param parent
     * @param position
     * @return
     */
    protected abstract View getViewByPosition(View convertView, ViewGroup parent,int ... position);
    
    其余的直接看源码吧。图片在下面：
    ![](https://github.com/RnMonkey/MultiListView/blob/master/1.png )
