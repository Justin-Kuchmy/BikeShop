export class ListObjectWrapper<T> {
    public objectList: T[];
  
    constructor(objectList: T[] = []) {
      this.objectList = objectList;
    }
  }
  